package am.ze.wookoo.infixnotation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculate.*

class Calculate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        val Numbers = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9)
        for (i in Numbers){
            i.setOnClickListener {
                Log.d("숫자 버튼",i.toString());
                Toast.makeText(this, "누른 숫자 :${i.text}",Toast.LENGTH_SHORT).show()
                Display.text = Display.text.toString() + i.text.toString()
            }
        }
        val Signs = arrayOf(buttonPlus,buttonMinus,buttonMultiply)
        for (i in Signs){
            i.setOnClickListener {
                Log.d("기호 버튼",i.text.toString())

                var StringTemp:String = Display.text.toString()
                Toast.makeText(this,"스트링 : ${StringTemp} 마지막 인덱스 :${StringTemp.lastIndex}",Toast.LENGTH_SHORT).show()
                if (StringTemp.lastIndex >= 0 ){
                    val X = StringTemp[StringTemp.lastIndex]
                    Toast.makeText(this,"누른 X 기호 : ${X}",Toast.LENGTH_SHORT).show()
                    when(X){
                        '+','-','+','*' -> Display.text = Display.text.toString().substring(0,StringTemp.lastIndex)  + i.text.toString()
                        else -> Display.text = Display.text.toString() + i.text
                    }
                }
            }
        }


        buttonClear.setOnClickListener {
            Display.text = ""
        }

    }
}
