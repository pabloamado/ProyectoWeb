package handler;

import entity.Account;
import entity.ExceptionWriter;
import entity.MailSender;
import entity.MailToSend;
import mapper.LoginMapper;
import response.LoginResponse;
import validator.AccountValidator;

public class LoginHandler {

	private static final int MAX_ATTEMPTS = 3;
	private LoginMapper loginMapper;
	private AccountValidator validator;
	private AccountHandler accHandler;
	private MailSender mailSender;

	public LoginHandler(LoginMapper loginMapper, AccountValidator validator, AccountHandler accHandler,
			MailSender mailSender) {
		this.loginMapper = loginMapper;
		this.validator = validator;
		this.accHandler = accHandler;
		this.mailSender = mailSender;
	}

	// FUNCION QUE DEVUELVE OBJETO LOGINRESPONSE LUEGO DE BUSQUEDAS Y VALIDACIONES
	public LoginResponse getLoginResponse(String email, String enteredPassword) {

		LoginResponse response = new LoginResponse();

		if (!validator.mailIsValid(email)) {

			response.setMsg("Los datos ingresados no tiene el formato correcto,asegurese de ingresar una"
					+ " direccion de mail y contraseña validas.");

		} else {

			Account account = accHandler.getAccountWithMail(email);

			if (account == null) {

				response.setMsg("No hay ninguna cuenta asociada al mail ingresado.");

			} else if (account.isBlocked()) {

				response.setMsg("La cuenta a la que intentas acceder ha sido bloqueada.");

			} else if (account.isDeleted()) {

				response.setMsg("La cuenta a la que intentas acceder ha sido borrada.");

			} else if (!validator.equals(enteredPassword, account.getPassword())) {

				String aditionalMsg = processFailAttempt(account);
				response.setMsg("La contraseña es erronea. " + aditionalMsg);

			} else {

				response.setSuccess(true);

				if (account.getAttempts() != 0) {

					changeToZeroAttempts(account);
				}

				response.setAccount(account);

			}

		}

		return response;
	}

	// FUNCION QUE AÑADE INTENTOS FALLIDO A LA TABLA DE LA CUENTA DE LA DB
	private String processFailAttempt(Account account) {
		String msg = "";

		if (account.getAttempts() < MAX_ATTEMPTS) {

			updateAttempt(account);

		} 
		
		if (account.getAttempts() == MAX_ATTEMPTS) {

			boolean success = blockAccount(account);

			if (success) {
				
				MailToSend preparedMail = mailSender.prepareBlockMail(account.getMail());
				mailSender.sendEmail(preparedMail);
				msg = "Su usuario fue bloqueado.";
				
			}

		} else {

			msg = "Le quedan " + (MAX_ATTEMPTS - account.getAttempts()) + " intentos.";
		}

		return msg;
	}

	// FUNCION QUE CAMBIA A 0 INTENTOS DE INGRESO EN LA DB
	private void changeToZeroAttempts(Account account) {

		account.setAttempts(0);
		
		try {

			loginMapper.updateAccountAttempts(account);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

	}

	//FUNCION QUE SUMA 1 INTENTO DE INGRESO EN LA DB
	private void updateAttempt(Account account) {

		account.addAttempt();

		try {

			loginMapper.updateAccountAttempts(account);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}

	}
	
	//FUNCION QUE BLOQUEA LA CUENTA EN LA DB
	private boolean blockAccount(Account account) {
		
		boolean success=false;
		
		try {

			success = loginMapper.blockAccount(account);

		} catch (Exception e) {

			e.printStackTrace(ExceptionWriter.getStream());
			e.printStackTrace();
		}
			return success;
		}

}
