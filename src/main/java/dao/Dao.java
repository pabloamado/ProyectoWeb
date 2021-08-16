package dao;

import java.util.HashMap;
import java.util.List;

import entity.ConnectionDb;

public class Dao {

	//HACE UN INSERT O UPDATE
    public boolean doOperation(String query, HashMap<Integer, Object> map) {

        return ConnectionDb.getConnectionDb().saveData(query, map);
    }

    //SELECT
    public List<HashMap<String, Object>> getEntity(String query, HashMap<Integer, Object> map) {

        return ConnectionDb.getConnectionDb().getEntity(query, map);

    }
 
   // INSERT O UPDATE PARA POSTEOS Y COMENTARIOS
    public int getIdGenerated(String query, HashMap<Integer, Object> map) {
    	
    	 return ConnectionDb.getConnectionDb().getIdGenerated(query, map);
    }

    //USADA UNICAMENTE PARA BORRAR PUBLICACIONES
	public boolean deletePostTransaction(String query1, String query2, 
			HashMap<Integer, Object> map1, HashMap<Integer, Object> map2) {

        return ConnectionDb.getConnectionDb().executeTransaction(query1, query2, map1, map2);
    }

}

