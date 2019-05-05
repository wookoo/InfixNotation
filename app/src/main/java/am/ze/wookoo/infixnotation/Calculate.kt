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
        val Numbers = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,buttonBracketEnd,buttonBracketStart) //0~9 버튼을 배열로 만든다
        for (i in Numbers){ //0~ 9버튼을 눌렀을때 하는 일 for 문으로 돌려버림
            i.setOnClickListener { //클릭 리스너 설정
                Log.d("숫자 버튼",i.toString());//로그확인
                Display.text = Display.text.toString() + i.text.toString()
            }
        }
        val Signs = arrayOf(buttonPlus,buttonMinus,buttonMultiply,buttonDivde)

        for (i in Signs){
            i.setOnClickListener {
                Log.d("기호 버튼",i.text.toString())

                var StringTemp:String = Display.text.toString()
               // Toast.makeText(this,"스트링 : ${StringTemp} 마지막 인덱스 :${StringTemp.lastIndex}",Toast.LENGTH_SHORT).show()
                if (StringTemp.lastIndex >= 0 ){
                    val X = StringTemp[StringTemp.lastIndex]
                    //Toast.makeText(this,"이전 기호: ${X} 누른 기호 : ${i.text}",Toast.LENGTH_SHORT).show()
                    when(X){
                        '+','-','÷','*' -> Display.text = Display.text.toString().substring(0,StringTemp.lastIndex)  + i.text.toString()
                        else -> Display.text = Display.text.toString() + i.text
                    }
                }
            }
        }

        buttonClear.setOnClickListener { //클리어 버튼누르면
            Display.text = "" //디스플레이의 텍스트 초기화
        }
        buttonDel.setOnClickListener {
            if(Display.text.toString().lastIndex >=0){ //글자가 1개 이상이면
                Display.text = Display.text.toString().substring(0,Display.text.toString().lastIndex) //스트링을 하나 날려버린다
            }
        }

        buttonEqual.setOnClickListener {
            val BracketStack = Stack()
            val tempString:String = Display.text.toString()
            val StringSize = tempString.lastIndex
            var IsTrue = true
            Log.d("스트링 사이즈 " ,StringSize.toString())
            if (StringSize != -1){
                for(i in 0..StringSize){
                    val tempChar = tempString[i]
                    //Log.d("TempChar",tempChar.toString())
                    if(BracketStack.is_empty() && tempChar == ')'){
                        IsTrue = false
                        break
                    }
                    if(tempChar == ')'){
                        BracketStack.pop()
                    }
                    else if(tempChar =='('){
                        BracketStack.push(3)
                    }

                }
                if(!IsTrue || !BracketStack.is_empty()){
                    Toast.makeText(this,"괄호가 올바르지 않습니다",Toast.LENGTH_SHORT).show()
                    //괄호쌍이 올바르지 않을 경우 출력
                }
                else{
                    //괄호가 올바를 경우 할 작업 > 1. 중위식 후위식 변환
                    //2. 중위식 후위식 평가

                    var ShowString:String =""
                    val ChangeStack = Stack()
                    for(i in 0..StringSize){
                        val sym = tempString[i]
                        if (sym == ')'){
                            var left:Char
                            while(true){
                                left = ChangeStack.pop().toChar()
                                if(left == '('){
                                    break
                                }
                                print(left)
                                ShowString += left.toString()
                            }

                        }
                        else if (pie(sym) == -1){
                            ShowString += sym.toString()
                            Log.d("PIE","수행")
                        }
                        else{
                            while(!ChangeStack.is_empty() && pis(ChangeStack.peek().toChar() )>= pie(sym)){
                                ShowString += ChangeStack.pop().toChar().toString()
                            }

                            ChangeStack.push(sym.toInt())
                            Log.d("스택관련","푸시 수행됨 들어간 값 : ${sym}")

                        }

                    }
                    while(!ChangeStack.is_empty()){
                        //Log.d("으악","${ChangeStack.pop().toChar()}")
                        ShowString += ChangeStack.pop().toChar().toString()
                    }
                    Log.d("변환된 식",ShowString)
                    Toast.makeText(this,"변환된 식  : ${ShowString}",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun pis(sym: Char): Int {
        when (sym) {
            '(' -> return 0
            ')' -> return 3
            '+', '-' -> return 1
            '*', '%', '÷' -> return 2
        }
        return -1
    }

    fun pie(sym: Char): Int {
        when (sym) {
            '(' -> return 3
            ')' -> return 0
            '+', '-' -> return 1
            '*', '%', '÷' -> return 2
        }
        return -1
    }

}
