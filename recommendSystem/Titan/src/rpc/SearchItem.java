package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import external.ExternalAPI;
import external.ExternalAPIFactory;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    double lat = Double.parseDouble(request.getParameter("lat"));
	    double lon = Double.parseDouble(request.getParameter("lon"));
	    String term = request.getParameter("term"); // Term can be empty or null.
	    
	    // Connect to external API
	    ExternalAPI api = ExternalAPIFactory.getExternalAPI();


	    List<Item> items = api.search(lat, lon, term);

	    // There should be some saveItem logic here
	    
	    // Convert Item list back to JSONArray for client
	    List<JSONObject> list = new ArrayList<>();
	    try {
	      for (Item item : items) {
	        // Add a thin version of restaurant object
	        JSONObject obj = item.toJSONObject();
	        list.add(obj);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    JSONArray array = new JSONArray(items);
	    RpcHelper.writeJsonArray(response, array);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
