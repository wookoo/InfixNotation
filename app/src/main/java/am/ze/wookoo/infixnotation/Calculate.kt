package am.ze.wookoo.infixnotation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculate.*
import kotlinx.android.synthetic.main.activity_calculate.view.*

class Calculate : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)
        val Numbers = arrayOf(button0,button1,button2,button3,button4,button5,button6,button7,button8,button9) //0~9 버튼을 배열로 만든다
        for (i in Numbers){ //0~ 9버튼을 눌렀을때 하는 일 for 문으로 돌려버림
            i.setOnClickListener { //클릭 리스너 설정

                var StringTemp:String = Display.text.toString()

                if (StringTemp.length <20 ){ //직접 지정한 숫자가 20 이 안 넘었을 경우
                    Display.text = Display.text.toString() + i.text //누른 버튼에 맞는 숫자를 띄워준다.
                }

            }
        }



        buttonBracketEnd.setOnClickListener { // ) 버튼을 눌렀을때 할 작업


                var StringTemp:String = Display.text.toString()
                if(Display.text.toString().length <20){
                    try{ //try 문을 사용하여 StringTemp.lastIndex 가 -1 이 아닐 경우에 수행된다.
                        val X = StringTemp[StringTemp.lastIndex] //마지막 문자를 X  변수에 저장한다.
                        when(X){
                            '0','1','2','3','4','5','6','7','8','9','(',')'-> Display.text = Display.text.toString() + buttonBracketEnd.text
                            //마지막 변수가 위와 같다면, 즉, 기호가 아니라면 누른 버튼에 맞는 ) 를 띄워준다.
                        }
                    }catch (e : Exception) {//StringTemp.lastIndex 가 -1 일때 수행된다
                        Display.text = Display.text.toString() + ")"  //사용자가 누른 ) 를 띄워준다.
                    }
                }
        }

        buttonBracketStart.setOnClickListener { //( 버튼을 눌렀을때 작업
            var StringTemp:String = Display.text.toString()
            if(Display.text.toString().length <20) {
                try {//try 문을 사용하여 StringTemp.lastIndex 가 -1 이 아닐 경우에 수행된다.
                    val X = StringTemp[StringTemp.lastIndex]
                    when (X) {
                        '+', '-', '÷', '*' ,'(',')'-> Display.text = Display.text.toString() + buttonBracketStart.text
                        //마지막 변수가 위와 같다면, 즉, 마지막 변수가 숫자가 아니라면, 누른 버튼에 맞는 (를 띄워준다.
                    }
                } catch (e: Exception) {//StringTemp.lastIndex 가 -1 일때 수행된다
                    Display.text = Display.text.toString() + "(" //사용자가 누른 ( 를 띄워준다.
                }
            }
        }


        val Signs = arrayOf(buttonPlus,buttonMinus,buttonMultiply,buttonDivde,buttonSquare) //기호 버튼을 눌렀을때 공통된 작업을 하므로 배열로 만든다.

        for (i in Signs){  //for each 문으로 배열 각각 요소인 버튼에 접근한다
            i.setOnClickListener { //버튼마다 작업을 지정해준다.
                Log.d("기호 버튼","${i.text}") //디버그용 로그를 출력

                var StringTemp = Display.text.toString() //띄워져있는 text 를 가져온다.
                if (StringTemp.length> 0 && StringTemp.length <20 ){ //띄워져있는 text 의 길이가 0보다 크고 내가 설정한 20보다 작으면 수행한다.
                    val X = StringTemp[StringTemp.lastIndex]
                    when(X){
                        '+','-','÷','*' -> Display.text = Display.text.toString().substring(0,StringTemp.lastIndex)  + i.text.toString()
                        //마지막 변수가 위와 같으면, 즉, 마지막 변수가 기호면
                        //예를들어 이전이 456+ 고 내가 누른게 - 면, 456- 로 바뀐다.
                        '(' -> Display.text = Display.text //이전이 (면 괄호 시작에 기호가 올수 없으므로 아무 작업도 하지 않는다.
                        else -> Display.text = Display.text.toString() + i.text //그 외의 경우는 ) 나 숫자인 경우이므로 누른 버튼을 추가해준다.
                    }
                }
            }
        }

        buttonClear.setOnClickListener { //클리어 버튼누르면
            Display.text = "" //디스플레이의 텍스트 초기화
            Changed.text = ""
            Toast.makeText(this,"값이 삭제되었습니다",Toast.LENGTH_SHORT).show() //값이 삭제됬음을 띄워준다.
        }

        buttonDel.setOnClickListener {  //Del 버튼을 누르면
            if(Display.text.toString().lastIndex >=0){ //글자가 1개 이상이면
                Display.text = Display.text.toString().substring(0,Display.text.toString().lastIndex) //스트링을 하나 날려버린다
            }
            Changed.text = ""
        }

        buttonEqual.setOnClickListener { //계산 결과 버튼을 누르면 할 작업

            val BracketStack = Stack() //괄호쌍 검사를 위한 Stack 객체 생성
            val DisPlayString = Display.text.toString() //사용자가 입력한 String 들
            val LastIndex = DisPlayString.lastIndex
            var IsTrue = true //괄호가 올바른지. 기본값은 true
            var lastSymbol = DisPlayString[LastIndex]

            if (LastIndex != -1 && lastSymbol != '+' &&lastSymbol != '-' && lastSymbol != '÷'&& lastSymbol != '*' ){ //스트링에 값이 있으면
                for(i in 0..LastIndex){ //for each 문으로 스트링의 요소 하나하나를 가져온다
                    val sym = DisPlayString[i]
                    if(BracketStack.is_empty() && sym == ')'){ //스택이 비었는데 들어온 sym 이 ) 면
                        IsTrue = false //그 괄호쌍은 올바르지 않으므로
                        break //for 문 종료
                    }
                    if(sym == ')'){ //스택이 비지 않았고, 들어온 sym 이 ) 면
                        BracketStack.pop() //pop 을 수행한다.
                    }
                    else if(sym =='('){ //sym 값이 ( 인 경우 일단 값을 넣어본다.
                        BracketStack.push(3) //아무값이나 push 를 수행한다.
                    }

                }
                if(!IsTrue || !BracketStack.is_empty()){ //isTrue 가 false 거나, BracketStack 이 비지 않았을 경우

                    Toast.makeText(this,"괄호가 올바르지 않습니다",Toast.LENGTH_SHORT).show()
                    //괄호쌍이 올바르지 않을 경우 출력
                }
                else{
                    //괄호가 올바를 경우 할 작업 > 1. 중위식 후위식 변환

                    var ShowString ="" //사용자에게 변환된 중위식을 저장할 String 변수
                    val ChangeStack = Stack() //중위식을 후위식으로 변환 하는 것은 Stack 을 통해 구현을 한다.
                    for(i in 0..LastIndex){ //0~ 마지막 인덱스 만큼 for 문을 반복한다.
                        val sym = DisPlayString[i] //sym 변수는 DisPlayString 의 i 번째 index와 같다.
                        if (sym == ')'){ //sym 이 ) 인 경우
                            var left:Char //left 변수는 char 로 설정한다. 값 대입이 없으면, Kotlin 컴파일러가 무슨 타입인지 예측할수 없으므로, Char 로 이번엔 설정해준다.
                            while(true){ //코틀린은 while((변수=할당) == (조건)) 문이 수행이 안된다 그렇기 때문에 while True 로 수행한다.
                                left = ChangeStack.pop().toChar() //pop 을수행한 값을 left 에 넣는다.
                                if(left == '('){ //만약 left 가 ( 면
                                    break //while 이 종료된다.
                                }
                                ShowString += " ${left}" //그게 아니면 left 를 String 으로 변환후 ShowString 에 추가한다. 그전에, 앞에 공백을 추가하여 숫자와 기호를 구분한다.
                            }

                        }
                        else if (pie(sym) == -1){ //sym 의 pie 를 수행한 값이 -1 이면 숫자이므로
                            ShowString += sym.toString()//ShowString 에 추가한다.

                        }
                        else{ //그 외의 경우
                            if(sym != '(' && sym != ')'){ //그 외의 경우 중 ( 와 ) 가 아니면
                                ShowString += " " //기호와 숫자를 구분해야 하므로 ShowString 에 " "로 공백을 추가한다.
                            }
                            while(!ChangeStack.is_empty() && pis(ChangeStack.peek().toChar() )>= pie(sym)){ //스택이 비지 않았고, 연산자 우선순위등을 고려한다
                                ShowString += "${ChangeStack.pop().toChar()} " //연산자 또는 숫자는 공백으로 비교하므로 마지막에 " " 를 추가한다.
                            }

                            ChangeStack.push(sym.toInt()) //while 문이 끝났으면, 현재 연산자를 push 를 하여 스택에 저장한다.

                        }

                    }
                    while(!ChangeStack.is_empty()){ //for 문이 끝났으면, 스택이 빌때 까지 반복한다.
                        ShowString += " ${ChangeStack.pop().toChar()}" //공백 문자를 통해 연산자가 끝남을 확인시켜준다.
                    }
                    Log.d("변환된 식",ShowString) //디버그용 출력

                    //이제 결과값을 띄워주면된다.

                    //2. 중위식 후위식 평가진행하면 됨.
                    val result = CaculatePostfix(ShowString) //중위식 평가하는 함수를 호출한다. , 위 작업을 통하여 ShowString 은 입력된 중위식을 후위식으로 바꾼 상태다.
                    Changed.text = "변환된 식 : \n$ShowString\n\n계산결과 :\n$result"
                }
            }
            else{// String 의 끝에 들어있는 부호가 계산 기호일 경우
                Toast.makeText(this,"식이 올바르지 않습니다",Toast.LENGTH_SHORT).show()  //Toast Message 로 식이 올바르지 않다는것을 보여준다.
            }
        }

    }

    fun pis(sym: Char): Int { //중위식 , 후위식을 변환할때 확인하는 함수, 스택 안의 값을 확인해본다.
        when (sym) {
            '(' -> return 0 //190 번째줄과 return 순위가 달라져있다.
            '^' -> return 4 //제곱은 항상 제일 먼저 수행하게 된다.
            ')' -> return 3
            '+', '-' -> return 1
            '*', '%', '÷' -> return 2
        }
        return -1
    }

    fun pie(sym: Char): Int { //중의식, 후위식을 변환할떄 확인하는 함수, 스택 안의 값을 확인해본다.
        when (sym) {
            '(' -> return 3
            '^' -> return 4
            ')' -> return 0
            '+', '-' -> return 1
            '*', '%', '÷' -> return 2
        }
        return -1
    }

    fun CaculatePostfix(tempString : String):Int { //생성된 후위식을 출력하는 함수이다.
        val stack = Stack() //스택 객체를 생성해준다.

        val Strings = tempString.split(" ") //파라메터로 받아온 tempString 의 공백을 기준으로 split 을 한다.
        //스플릿이 된 값은 베열형태로 반환되서 Strings 에 저장된다
        for (i in Strings){ //Kotlin 에서 지원하는 for each 문을 사용한다.
            Log.d("스트링스","작업중인 값 : ${i}") //디버그를 해보기 위해 Log.d 메소드를 호출하여 로그를 찍어본다.
            // for each 문으로 돌리는 i 값이 출력된다.

            try{ //toInt 그러니까, i를 숫자로 바꿀수 있으면
                var temp = i.toInt()//자바에서 Integer.parseInt(String) 과 같다. > 스트링을 숫자만 추출하고
                stack.push(temp) //그 값을 stack 에 넣는다.
                Log.d("인트형으로 바꾼 것","$temp")
            }
            catch (e : Exception){ //catch 문에선 String 형태의 기호를 Int 형으로 못바꾼 것들이 실행된다.
                Log.d("인트형으로 못 바꾼것","$i")
                //이 아래는 적절히 후위식 평가를 진행하는 코드다.
                val left = stack.pop() //pop 을 두번 수행하여 left 와 now 를 가져온다.
                val now = stack.pop()
                when(i){
                    "+"  -> stack.push(now + left) //현재 i 가 + 면 덧셈 수행후 push
                    "-" -> stack.push(now - left) // 현재 i 가 - 면 뺄셈 수행후 push
                    "*" -> stack.push(now *left) //현재 i 가 * 면 곱셈 수행후 push
                    "^"-> stack.push(Math.pow(now.toDouble(), left.toDouble()).toInt()) //현재  i 가 ^면 Math 메소드의 pow 함수를 사용하여 제곱을 구한 후 push
                    "÷" -> stack.push(now/left) //현재 i 가 ÷ 면 나누기 수행후 push
                }

            }

        }
        return stack.pop() //for 문이 끝났으면 pop 을 수행하여 반환한다.

    }

}
