package am.ze.wookoo.infixnotation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this,Calculate::class.java))
        finish()

        //Handler().postDelayed({
         //   startActivity(Intent(this,Calculate::class.java))
          //  finish()
        //},0);
    }
}
