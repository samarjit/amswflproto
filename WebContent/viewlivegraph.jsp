<%@ page import="com.opensymphony.workflow.Workflow,
                 com.opensymphony.workflow.basic.BasicWorkflow,
                 com.opensymphony.workflow.spi.Step,
                 java.util.List" %>
<% response.setHeader("Pragma","no-cache");
  response.setDateHeader("Expires",0);
  response.setHeader("Cache-Control","no-cache");
%>


<%
Workflow wf = new BasicWorkflow((String) session.getAttribute("username"));
long id = Long.parseLong(request.getParameter("id"));
%>
<html>
<body>
<form action="#" method="GET">
id:<input name="id" value="<%= request.getParameter("id") %>"/>
xAdjust:<input name="xadjust" value="-8"/>
yAdjust:<input name="yadjust" value="-7"/>
<input type="submit" />
</form>

<div id="workflowCanvas" style="position:relative;height:566px;width:508px;">
<img src="eclipse.png" border=0/>
</div>
<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
<script type="text/javascript" src="js/xmlextras.js"></script>
<script type="text/javascript">
var currentStepIds = [];
<%List currentSteps = wf.getCurrentSteps(id);
for(int i = 0; i < currentSteps.size(); i++){%>
currentStepIds[<%=i%>] = <%=((Step) currentSteps.get(i)).getStepId()%>;
<%}%>

var xmlHttp = XmlHttp.create();
var async = true;
xmlHttp.open("GET", "newwfl.lyt", async);
xmlHttp.onreadystatechange = function () {
    if (xmlHttp.readyState == 4){
        //set up graphics
        var jg = new jsGraphics("workflowCanvas");
        jg.setColor("#ff0000");
        jg.setStroke(3);
       /* var xAdjust = 68;
        var yAdjust = -17;
        var widthAdjust = 3;
        var heightAdjust = 2;*/
        var xAdjust = -8;
        <%if(null != request.getParameter("xadjust")){%>
         xAdjust = <%=request.getParameter("xadjust") %>;
         document.getElementsByName("xadjust")[0].value=xAdjust;
        <%} %>
      
        var yAdjust = -7;
        <% if(null != request.getParameter("yadjust")){%>
         yAdjust = <%=request.getParameter("yadjust") %>;
         document.getElementsByName("yadjust")[0].value=yAdjust;
        <%} %>
         
        
        var widthAdjust = 3;
        var heightAdjust = 1;
        //parsing xml and paint;
        var cells = xmlHttp.responseXML.getElementsByTagName("cell");
        for(var i = 0; i < currentStepIds.length; i++){
            for(var n = 0; n < cells.length; n++){
                var cell = cells[n];
                if(cell.getAttribute("type") == "StepCell" && currentStepIds[i] == parseInt(cell.getAttribute("id")))
                    {
                    jg.drawRect(parseInt(cell.getAttribute("x")) + xAdjust, parseInt(cell.getAttribute("y")) + yAdjust, parseInt(cell.getAttribute("width")) + widthAdjust, parseInt(cell.getAttribute("height")) + heightAdjust);
                }
            }
        }
        jg.paint();
    }
};
xmlHttp.send(null);    
</script>

</body>
</html>