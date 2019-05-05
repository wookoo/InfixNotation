package am.ze.wookoo.infixnotation

open class Stack{
    var top = -1
    var data = ArrayList<Int>()
    fun is_empty():Boolean{
        return this.top == -1
    }
    fun push(n:Int){
        //top 이 이렇게 되면 안된다 수정!
        this.data.add(n)
        this.top+=1
    }
    fun pop():Int{
        if(!this.is_empty()){
            this.top--;
            val temp = this.data.get(this.data.lastIndex)
            this.data.removeAt(this.data.lastIndex)
            return  (temp)
        }
        return -1;


    }
    fun peek():Int{
        return this.data.get(this.top)
    }

}