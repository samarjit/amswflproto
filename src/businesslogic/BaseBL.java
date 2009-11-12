package businesslogic;

import java.io.Serializable;
import java.util.HashMap;

public interface BaseBL extends Serializable {
HashMap<String, String> processRequest(HashMap<String,String> hm);

}
