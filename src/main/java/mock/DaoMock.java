package mock;

import java.util.HashMap;
import java.util.List;
import dao.Dao;

public class DaoMock extends Dao{

	private boolean saved;
	private List<HashMap<String, Object>> list;
	private int idGenerated;
	private boolean deleted;
	
    public boolean doOperation(String query, HashMap<Integer, Object> map) {

        return saved;
    }

	public List<HashMap<String, Object>> getEntity(String query, HashMap<Integer, Object> map) {

        return list;
    }
 
    public int getIdGenerated(String query, HashMap<Integer, Object> map) {
    	
    	return idGenerated;
    }
    
	public boolean deletePostTransaction(String query1, String query2, 
			HashMap<Integer, Object> map1, HashMap<Integer, Object> map2) {

        return deleted;
    }
	
    public void setSaved(boolean saved) {
		this.saved = saved;
	}

	public void setList(List<HashMap<String, Object>> list) {
		this.list = list;
	}

	public void setIdGenerated(int idGenerated) {
		this.idGenerated = idGenerated;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
