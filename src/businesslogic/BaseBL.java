package businesslogic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public interface BaseBL extends Serializable {
HashMap  processRequest(Map buslogHm);
HashMap activitySubmit(Map hm);
 

}
