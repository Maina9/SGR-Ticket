package com.example.sgrticket

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.sgrticket.databinding.ActivityBookingBinding
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

private lateinit var binding: ActivityBookingBinding
class Booking  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
}
    companion object {
        class SignUpAsyncTask internal constructor(context: Context) : AsyncTask<String, String, String>() {
            lateinit var con: HttpURLConnection
            lateinit var resulta:String
            val builder = Uri.Builder()
            private val cont: Context =context
            override fun onPreExecute() {
                super.onPreExecute()

                val Destination: String = binding.Destination.text.toString()
                val from: String = binding.inputFrom.text.toString()
                val tdatetime: String = binding.inputTime.text.toString()

                val progressBar= ProgressBar(cont)
                progressBar.isIndeterminate=true
                progressBar.visibility= View.VISIBLE
                builder .appendQueryParameter("destination", Destination)
                builder .appendQueryParameter("from", from)
                builder .appendQueryParameter("tdatetime", tdatetime)
            }

            override fun doInBackground(vararg params: String?):  String? {
                try {

                    var query = builder.build().encodedQuery
                    val url: String = "https://sgrticket96.000webhostapp.com/sgr/Create.php"
                    val obj = URL(url)
                    con = obj.openConnection() as HttpURLConnection
                    con.setRequestMethod("POST")
                    con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")
                    con.setRequestProperty("Accept-Language", "UTF-8")
                    con.setDoOutput(true)
                    val outputStreamWriter = OutputStreamWriter(con.getOutputStream())
                    outputStreamWriter.write(query)
                    outputStreamWriter.flush()
                    Log.e("pass 1", "connection success ")
                } catch (e: Exception) {
                    Log.e("Fail 1", e.toString())
                }
                try {
                    resulta = con.inputStream.bufferedReader().readText()
                    Log.e("data", resulta)
                } catch (e: java.lang.Exception) {
                    Log.e("Fail 2", e.toString())
                }
                return resulta;
            }
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)

                var json_data = JSONObject(resulta)
                val code: Int = json_data.getInt("code")
                Log.e("data",code.toString())
                if (code == 1) {
                    //val com: JSONArray = json_data.getJSONArray("userdetails")
                    //val comObject = com[0] as JSONObject
                    //Log.e("data",""+comObject.optString("fname"))

                    val toMain = Intent(cont, MainActivity::class.java)
                    toMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    cont.startActivity(toMain)

                }
            }
        }

    }

}