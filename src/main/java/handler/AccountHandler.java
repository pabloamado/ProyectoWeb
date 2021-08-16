package handler;

import entity.Account;
import entity.MailToSend;
import entity.CodeGenerator;
import entity.ExceptionWriter;
import entity.MailSender;
import mapper.AccountMapper;
import mapper.CodeMapper;
import response.GenericResponse;
import response.NicknameResponse;
import validator.AccountValidator;

public class AccountHandler {

	private static final int LOGGED_FLAG = 0;
	private static final int UNLOGGED_FLAG = 1;
	private AccountMapper accountMapper;
	private CodeMapper codeMapper;
	private AccountValidator validator;
	private MailSender mailSender;

	public AccountHandler(AccountMapper accountMapper, CodeMapper codeMapper, AccountValidator validator,
			MailSender mailSender) {

		this.accountMapper = accountMapper;
		this.codeMapper = codeMapper;
		this.validator = validator;
		this.mailSender = mailSender;
	}

	// FUNCION QUE PREPAR EL MENSAJE DE RESPUESTA AL INTENTAR REGISTRAR LA CUENTA
	public String getMsgRegisterAccount(Account account) {

		String responseMsg = "Ya existe una cuenta asociada al mail introducido.";

		if (!validator.accountIsValid(account)) {
			responseMsg = "Los datos ingresados no son validos, vuelve a intentarlo.";

		} else {

			Account accountRecovered = getAccountWithMail(account.getMail());

			if (accountRecovered == null) {

				responseMsg = saveAccount(account) ? "Se registro exitosamente."
						: "Ha ocurrido un error con el servidor mientras se intentaba registrar.";

			} else if (accountRecovered.isBlocked()) {

				responseMsg = "Ya existe una cuenta asociada al mail introducido pero se encuentra bloqueada";

			} else if (accountRecovered.isDeleted()) {
				account.setId(accountRecovered.getId());
				responseMsg = reactivateAccount(account) ? "Hemos reactivado su cuenta ya que previamente fue borrada."
						: "Ha ocurrido un error al intentar reactivar su cuenta.";
			}

		}

		return responseMsg;
	}

	// FUNCION QUE PREPAR EL MENSAJE DE RESPUESTA AL ACTUALIZAR EL APODO
	public NicknameResponse getUpdateNicknameResponse(String nick, int id) {

		NicknameResponse response = new NicknameResponse();
		response.setMsg("No se ha podido cambiar el apodo");

		if (!validator.nickNameIsValid(nick)) {

			response.setMsg("La longitud del apodo no es valido");

		} else if (updateNickname(nick, id)) {

			response.setNickname(nick);
			response.setMsg("Se ha cambiado el apodo");
		}

		return response;
	}

	// FUNCION QUE PREPARA EL MENSAJE DE RESPUESTA AL ACTUALIZAR EL MAIL
	public String getMsgUpdateMail(String newMail, String repeatedNewMail, String actualMail, int accountId) {

		String responseMsg = "";

		if (!validator.mailIsValid(newMail) || !validator.mailIsValid(repeatedNewMail)
				|| !validator.mailIsValid(actualMail)) {

			responseMsg = "El formato del mail no es valido";

		} else if (!validator.equals(newMail, repeatedNewMail)) {

			responseMsg = "Los mails ingresados no son iguales";

		} else if(getAccountWithMail(newMail)!=null){
					
				responseMsg = "Ya existe una cuenta asociada al mail al que se pretende cambiar";
				
		}else {

				Account account = getAccountWithId(accountId);

				if (account != null) {

					if (!account.getMail().equals(actualMail)) {
						
						responseMsg = "Su mail ingresado no es su mail actual.";

					} else if (!account.getMail().equals(newMail)) {

						responseMsg = updateMail(newMail, accountId) ? "El mail se actualizo correctamente."
								: "La direccion de mail no pudo actualizarse.";
					}

				}

			}

		return responseMsg;
	}

	// FUNCION QUE PREPARA EL MENSAJE DE RESPUESTA AL ACTUALIZAR LA CONTRASEÑA
	// ESTANDO LOGUEADO
	public String getMsgUpdatePassword(String newPass, String repeatedNewPass, String actualPass, int accountId) {

		String responseMsg = "La nueva contraseña debe ser diferente a la actual";

		if (!validator.passwordIsValid(newPass) || !validator.passwordIsValid(repeatedNewPass)
				|| !validator.passwordIsValid(actualPass)) {

			responseMsg = "La contraseña ingresada debe tener un formato valido.";

		} else if (!validator.equals(newPass, repeatedNewPass)) {

			responseMsg = "Las contraseñas deben coincidir.";

		} else {

			Account account = getAccountWithId(accountId);

			if (account != null) {

				if (!account.getPassword().equals(actualPass)) {
					responseMsg = "Su contraseña es incorrecta.";

				} else if (!actualPass.equals(newPass)) {

					responseMsg = updatePassword(newPass, account.getMail(), LOGGED_FLAG)
							? "La contraseña se ha actualizado exitosamente."
							: "La contraseña no se ha podido actualizar.";
				}

			}

		}

		return responseMsg;
	}

	// FUNCION QUE PREPARA EL MENSAJE DE RESPUESTA AL ENVIAR EL MAIL DE OLVIDAR
	// CONTRASEÑA
	public GenericResponse getSendForgotMailResponse(String mail) {

		GenericResponse response = new GenericResponse();

		if (!validator.mailIsValid(mail)) {

			response.setMsg("El formato del mail no es valido.");

		} else {

			Account account = getAccountWithMail(mail);

			if (account == null) {

				response.setMsg("No hay una cuenta asociada al mail ingresado.");

			} else {

				String code = new CodeGenerator(60).nextString();
				if (codeMapper.saveCode(account.getId(), code)) {

					MailToSend preparedMail = mailSender.prepareForgotMail(mail, code);

					if (mailSender.sendEmail(preparedMail)) {

						response.setMsg("Se ha enviado un correo a tu casilla de mail.");
						response.setSuccess(true);

					} else {

						response.setMsg("Ocurrio un error, vuelva a intentarlo.");
					}

				}

			}

		}

		return response;
	}

	// FUNCION QUE PREPARA EL MENSAJE DE RESPUESTA AL ACTUALIZAR LA CONTRASEÑA
	// OLVIDADA O POR CUENTA BLOQUEADA
	public GenericResponse getUpdatePassResponse(String newPass, String repeatedNewPass, 
			String mail, String code) {
		
		GenericResponse response = new GenericResponse();

		if (!validator.passwordIsValid(newPass) || !validator.passwordIsValid(repeatedNewPass)) {

			response.setMsg("La contraseña ingresada no tiene un formato valido.");

		} else if (!validator.equals(newPass, repeatedNewPass)) {

			response.setMsg("Las contraseñas deben coincidir.");

		} else {

			String recoveredCode = codeMapper.getCodeWithMail(mail);

			if (!code.equals(recoveredCode)) {

				response.setMsg("El mail ingresado o el codigo son incorrectos.");

			} else if (codeMapper.restartCode(mail)) {

				if (updatePassword(newPass, mail, UNLOGGED_FLAG)) {

					response.setMsg("La contraseña se ha actualizado exitosamente.");
					response.setSuccess(true);

				} else {

					response.setMsg("La contraseña no se ha podido actualizar.");
				}

			}
		}

		return response;
	}

	// CAMBIA EL ESTADO DE LA CUENTA A BORRADO,
	public boolean deletedAccount(int accountId) {

		boolean deleted = false;

		try {
			deleted = accountMapper.deleteAccount(accountId);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return deleted;
	}

	// INFORMA SI SE REGISTRO CORRECTAMENTE LA CUENTA
	private boolean saveAccount(Account account) {
		boolean success = false;

		try {

			success = accountMapper.saveAccount(account);

			if (success) {

				MailToSend mailToSend = mailSender.prepareRegisterMail(account);
				mailSender.sendEmail(mailToSend);
			}

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return success;
	}

	// ACTUALIZO LA CUENTA EN CASO DE QUE LA MISMA ESTE EN ESTADO BORRADO
	private boolean reactivateAccount(Account account) {

		boolean success = false;

		try {
			success = accountMapper.updateAccount(account);

			if (success) {

				MailToSend mailToSend = mailSender.prepareReactivateMail(account);
				mailSender.sendEmail(mailToSend);
			}

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return success;

	}

	// ACTUALIZA EL NICK ESTANDO LOGUEADO
	private boolean updateNickname(String nick, int id) {

		boolean updated = false;
		try {

			updated = accountMapper.updateNickname(nick, id);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return updated;
	}

	// ACTUALIZA EL MAIL ESTANDO LOGUEADO
	private boolean updateMail(String newMail, int accountId) {

		boolean updated = false;

		try {
			updated = accountMapper.updateMail(newMail, accountId);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return updated;
	}

	// ACTUALIZA LA CONTRASEÑA ESTANDO LOGUEADO
	private boolean updatePassword(String newPass, String mail, int flag) {

		boolean update = false;

		try {

			update = accountMapper.updatePassword(newPass, mail, flag);

			if (update) {

				MailToSend preparedMail = mailSender.prepareChangePassMail(mail);
				mailSender.sendEmail(preparedMail);
			}
		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return update;
	}

	// BUSCA LA CUENTA A PARTIR EL MAIL PROPORCIONADO, SI NO EXISTE DEVUELVE UN NULL
	public Account getAccountWithMail(String mail) {
		Account account = null;

		try {
			account = accountMapper.getAccountWithMail(mail);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

		return account;

	}

	// RECUPERA LA CUENTA CON LA MAYOR PARTE DE LOS DATOS
	public Account getAccountWithId(int id) {
		Account account = null;

		try {
			account = accountMapper.getAccountWithId(id);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
		return account;
	}

	// RECUPERA LA CUENTA CON UNA PARTE DE LOS DATOS
	public Account getFriendAccountWithId(int friendId) {

		Account account = null;

		try {
			account = accountMapper.getFriendAccountWithId(friendId);

		} catch (Exception e) {
			
			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
		return account;

	}

}
