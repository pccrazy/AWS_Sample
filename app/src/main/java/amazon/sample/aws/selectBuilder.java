package amazon.sample.aws;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class selectBuilder {

  private String table = "";
  private String coulm = "";
  private String condtion="";
  private Context context;

  public selectBuilder(Context context) {
    this.context=context;
  }

  public selectBuilder Select(String coulm) {
    this.coulm = coulm;
    return this;
  }

  public selectBuilder From(String table) {
    this.table = table;
    return this;
  }
  public selectBuilder Where(String condtion) {
    this.condtion = condtion;
    return this;
  }

 
  public void build(TextHttpResponseHandler httpResponseHandler) throws Exception {
    if (coulm == "") {
      coulm = "*";
    }
    if (table == "") {
      throw new Exception("NO TABLE IS SELECTED");
    }
    if (condtion != "") {
      condtion = "WHERE " + condtion;
    }

    RequestParams p = new RequestParams();
    p.put("Query", "SELECT " + coulm + " FROM " + table + " " + condtion);
    AsyncHttpClient client = new AsyncHttpClient();
    String url = "http://54.172.87.151:3000/query";
    client.post(context, url, p, httpResponseHandler);


  }

} 