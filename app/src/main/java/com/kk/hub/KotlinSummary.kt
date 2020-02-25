package com.kk.hub

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.kk.hub.model.bean.User
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 *  语言只是一个工具
 *  在我来看来Kotlin就是Java2.0 本质就是基于jvm的一个扩展库
 *  所谓的高级虚拟机语言特性:作用域函数、函数式编程、协成
 *  这些Java都能实现 只是oracle不作为 oracle这公司迟早要死 建议赶紧送给google吧
 *  直到现在还有很多Java开发并不能熟练使用JDK8的一些新特性(除了switch语句) JDK已经出到12了！！
 *
 *  编译可真尼玛的慢 为什么编译慢 因为多了大量的语法糖啊 编译后就打回原形了 和java并无区别
 *
 *  有一个现象我很不明白 就是会了Kotlin就瞧不起Java是一种什么心态  这和一个开发的编程能力有什么相关的
 *  说实话我不太喜欢Jake Wharton这个人 因为喜欢瞎jb带节奏 就是因为此人极力推荐Kotlin
 *  导致大量JW的信徒发了疯一样去追随
 *
 *  Kotlin中一切皆对象,不存在基本类型,函数也是一个对象。
 *  代码中 除了循环大多数控制结构都是表达式 表达式也是有值的
 *  能敲一行的代码 决不敲两行代码 能敲一个字符 决不敲五个字符
 *  代码少 极端的追求简洁真的好吗? 难道毛子心里变态?
 *
 *  Kotlin写起来确实简洁 至少目前除了反射和泛型支持的太烂 其他的都可接受
 *  各种内联函数写起来方便了一些 函数形参能传递函数本身(不用写各种回调地狱了) 代码飘逸了不少
 *  由于协成的特性 一些rx打头的库可以滚了 这点我非常喜欢 也是我学Kotlin真正的兴趣点
 *  不过Kotlin在IDE下语法分析太耗性能了 编码速度很快的时候IDE半天反应不过来 在使用Java时很少会有
 *  从某种意义上说 效率一定是会提升一些的 并不能解决开发的痛点
 *
 *                                  - by kk  on 2019/9/31  13:55
 */
class KotlinSummary {

    /*
        所有对象都通过一个指针所持有 而指针只有两种类型
        var 可变属性 表示指针可变
        val 不可变属性 表示指针不可变
        所有对象都可以明确指明可空或者非空属性 表示这个对象是否可能为null

        对于可空类型的对象 直接调用其方法 在编译阶段就会报错(编译期杜绝空指针异常)
     */
    var age: String? = "2333"       //类型加?表示可为空 直接调用age的方法 编译时会报错

    val age1 = age?.toInt()         //不作处理返回null

    val age2 = age?.toInt() ?: -1   //age为空时返回-1

    val age3 = age!!.toInt()        //为空时会抛空指针


    /*
        java:

        if(view !=null){
            if(view.getParent()!=null){
                if(view.getParent() instanceof ViewGroup){
                    ((ViewGroup)view.getParent()).removeView(view);
                }
            }
        }

        kotlin:

        (view?.parent as? ViewGroup)?.removeView(view)
        以上述代码为例，若view == null,则后续调用均不会执行，整个表达式 直接返回null，也不会抛出异常
        也就是说?表达式中,只要某个操作对象为null，则整个表达式直接返回null

        val v = a?.b | ? c
        以|为分割线 如果前部分为null,则整个表达式返回值等于c的值,否则等于前面部分的值
        相当于三元运算符 kotlin中没有三元运算符 因为kotlin中一些关键字本身就是表达式
    */

    /*
        val nameA:String
        此代码会编译报错Property must be initialized or be abstract

        由于Kotlin中默认是空安全的 属性必须初始化或抽象 任何属性的声明都必须有初始化值
        如果支持可空? 才能把属性声明为null 然而这样经常不方便 属性可以通过依赖注入来初始化

        这种情况下 并不能在构造函数提供一个非空初始器 但仍然想在类中引用该属性时避免空检查
        为了处理这种情况 可以使用lateInit

        lateinit修饰符只能用于类体中 不能在主构造中
        只能声明var属性 并且仅当该属性没有自定义getter或setter时
        该属性必须是非空类型 并且不能是原生类型(Int、Long、Float)
  */
    lateinit var kk: String //可以避免以上情况

    /*
        by lazy 惰性初始化
        直到第一次访问该属性的时候 才根据需要创建对象的一部分
        当初始化过程开销很大并使用对象时并不总是需要数据时 可用by lazy

        lazy是一个函数 操作符是线程安全的 接收一个表达式作为参数
        返回一个lazy类型的实例 这个实例可以作为委托 实现延时加载属性

        by lazy 只能作用于val关键字标注的属性
        当属性使用到的时候才会初始化lazy{}里的内容
        再次访问该属性的时候 只会得到结果 不会再执行lazy{}中的逻辑
     */
    val kkB: String by lazy {
        println("startLazy")
        "123"
    }

    /*
        x的操作和访问被EXBy所委托 当访问x时会执行EXBy的getValue方法 当操作X时会执行EXBy的setValue方法
        类似装饰模式和代理模式
     */
    var x: String by EXBy("a", "b")
    var y: Int by EXBy("b", 2)

    fun testBy() {
        x = "sad"
    }

    class EXBy<T>(private val a: String, private val value: T) {

        operator fun setValue(thisRef: Any?, property: KProperty<*>, t: T) {

        }

        @Suppress("UNCHECKED_CAST")
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            val s: Any = when (value) {
                is Int -> 2
                is Long -> 32131313212313121L
                is String -> "asd"
                is Float -> 2.3
                is Boolean -> false
                else -> -1
            }
            return s as T
        }

    }


    val test = "kk"

    val d = "First content is $test" //可以在字符串中直接访问变量和使用表达式


    /*
     kotlin中使用when语句替代switch
     when将它的参数和所有分支条件顺序比较 直到某个分支漫天条件 如果其他分支都不满足条件将会进入else分支

     when语句也可以给if else 链替换掉
    */
    fun testwhen(x: Any) {
        when (x) {
            1 -> print("x ==1")
            2 -> print("x ==2")
            else -> {
                print("x is neither 1 nor 2")
            }
        }

        when (x) {
            in 1..10 -> print("x is in the range") //1-10会执行此逻辑
            !in 10..20 -> print("x is outside the range") //不再10-20会执行此逻辑
            is String -> print("x is a string")
            else -> print("none of the above")
        }
    }

    /*
        这是一个扩展函数 被扩展类型为MutableList 给MutableList 添加一个swap函数
        也就是说此方法只能被MutableList调用 类似于装饰者的设计模式
        同时对于这个扩展对于使用者来说也是透明的
        使用者在使用该类的扩展功能时 就像使用这个类自身的功能一样
        对扩展解耦 业务层代码无侵入性

     */
    fun <T> MutableList<T>.swap(index1: Int, index2: Int) {

        val aa = null
        /*
            kotlin允许扩展函数这个接受者为null
            扩展函数中可以拿到接受者对象的指针 即this指针
         */
        val toString = aa.toString()

    }

    fun Any?.toString(): String {
        if (this == null) return "null"
        //空检测后 "this"会自动转换为非空类型 所以下面的toString()解析为Any类的成员函数
        return toString()
    }

    /*
        object 可以使用 直接创建一个继承某个类型的匿名类对象
        无需创建这个对象的类 在java中则需要创建一个class来继承TextWatcher
     */
    fun testObject(ediText: EditText) {
        ediText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        //一些场景我们只需要一个对象而已 并不需要特殊超类型 比如在函数内部 需要零碎地使用一些一次性的对象时
        val abc = object {
            var x: Int = 1
            var y: Int = 2
            val user: User = User()
        }
        print(abc.x + abc.y)
    }
}

//只是声明了一个对象 类似于java中的单例模式
object EXClick : View.OnClickListener {
    override fun onClick(v: View?) {

    }
}

/*
    Delegates.observable() 函数接收两个参数
    第一个初始化值 第二个是属性值变化事件的响应器handler 采用了观察者模式
    当被观察属性的值发生变化时会相应器handler会被调用 并执行lambda表达式
    同时响应器会收到三个参数：被赋值的属性, 赋值前的旧属性值, 以及赋值后的新属性值。
 */
var name: String by Delegates.observable("kk",
    { property: KProperty<*>, oldValue: String, newValue: String ->

        println("kProperty:  ${property.name} | oldName:$oldValue | newName : $newValue ")

    })


fun main(args: Array<String>) {

    println("name: $name")

    //当name发生变化时改变他的值都会自动回调{kProperty,oldName,newName->} 这个lambda表达式
    name = "zhang"
    name = "li"
}

/*
    kotlin中一切皆是对象 函数也一样
    在java中实现回调 需要声明接口 并传递接口的实现类
    但kotlin不需要 因为函数本身就是一个对象 通过传递函数就可以实现回调
 */
class MyScrollView {
    var onScrollListener: (Int, Int) -> Unit = { i, j -> }

    fun invokeScroll() {
        onScrollListener.invoke(2, 4)
    }
}

//此函数接收的参数是一个函数
fun testCallback(onScrollListener: (String, String) -> Unit) {

    onScrollListener.invoke("asdasd", "asd")
    val myScrollView = MyScrollView()
    myScrollView.onScrollListener = { i: Int, j: Int ->
        print(i + j)
    }
}


/*
    kotlin中构造需要声明constructor
    如果构造没有特意声明注解和可见性修饰符时  constructor 关键字可以省略
 */
class TestModel constructor(
    val name: String,
    var age: Int//此处相当于构造传递参数name、age为成员属性
) {
    private var address: String = ""

    //初始化语句
    init {
        //do something
    }

    //次级构造必须由柱构造调用 否则编译会报错Primary constructor call expected
    constructor(address: String) : this("", 2) {
        this.address = address
    }

    fun dotest() {}
    fun doA() {}
}

/*
    let函数调用T类型的let函数 该对象为函数的参数 在函数快内可以通过it指该对象
    返回值为函数块的最后一行或指定的return表达式
    使用场景1.需要针对一个可 null的对象统一做判空处理
           2.需要明确一个变量所处特定的作用于范围内可以使用
 */
fun testLet() {
    val a = TestModel("", 1)
    a.let {
    }
}

/*
     with函数不是以扩展的形式存在的 它是将某对象作为函数的参数 在函数块内可以通过this指代该对象
     返回值为函数块的最后一行或指定return表达式

     fun testWith(model: TestModel): Int {
       val result = with(model) {
       doA()
        dotest()
        2000
      }
    }
    with函数适一段逻辑大量调用同一个对象的多个方法时
    可以省去类名重复 {}作用于内可直接调用实例的公共属性和方法
 */

fun testWith(model: TestModel): Int =
    with(model) {
        doA()
        dotest()
        2000
    }

/*
    run函数相当于let和with两个函数的结合体,run函数只接受一个lambda函数为参数
    以闭包形式返回 返回值为最后一行的值或指定的return表达式
    适用于let、with函数任何场景
    可以直接访问实例的共有属性和方法 也可以在函数一样做判空处理
 */
fun testRun(holder: EXHolder, model: TestModel) {
    with(model) {
        holder.a = name
        holder.b = age
    }
}

class EXHolder(v: View) : RecyclerView.ViewHolder(v) {

    var a: String = ""
    var b: Int = -1

}

/*
    apply函数与run函数很像 唯一不同点是他们各自返回值不一样
    run函数是闭包形式返回最后一行代码的值 apply函数的返回时传入对象的本身
    apply函数一般用于一个对象实例初始化的时候 要对对象中的属性赋值
 */
fun testApply() {

    val model = TestModel("s", 2)

    val result = model.apply {
        print(name)
        print(age)
        1000
    }
    print("result: $result")
}

/*
    also函数实际上和let很像 唯一区别返回值不一样
    let闭包形式返回 返回函数体内最后一行的值 如果最后一行为空则返回一个Unit默认值
    also函数返回的是传入对象的本身
 */
fun testAlso() {
    val result = "adsasdasd".also {
        print(it.length)
        1000
    }
    print(result)
}

/*
    let、with、run、apply、also函数区别

    obj.let{}       需要用it为访问当前对象
                    闭包形式返回函数式最后一行的值或return表达式
                    是扩展函数
                    适用于处理某个对象不为null的操作场景 简化对空对象的访问

    obj.also{}      it指代当前对象
                    返回this
                    是扩展函数
                    适用于let函数的任何场景
                    一般可用于多个扩展函数链式调用

    with(obj){}     his为当前对象可省略
                    闭包形式返回函数式最后一行的值或return表达式
                    不是扩展函数
                    适用于需要调用一个类多个方法时 可以省去类名重复 省去一个变量的多次声明
                    {...}中可以直接调用类的方法

    obj.run{}       this指当前对象或省略
                    闭包形式返回函数式最后一行的值或return表达式
                    是扩展函数
                    适用于let、with函数任何场景

    obj.apply{}     this指代当前对象或省略
                    返回this
                    是扩展函数
                    1.适用于run函数的任何场景
                    2.一般用于初始化一个对象实例的时候 操作对象属性 最终返回该对象
                    3.可用于多个扩展函数链式调用
                    4.数据model多层级包裹判空处理的场景



 */


/**
 *
 * 协成官方描述：
 *      协程通过将复杂性放入库来简化异步编程。程序的逻辑可以在协程中顺序地表达，而底层库会为我们解决其异步性。
 *      该库可以将用户代码的相关部分包装为回调、订阅相关事件、在不同线程（甚至不同机器）上调度执行，
 *      而代码则保持如同顺序执行一样简单
 *
 *  协成 运行的单线程中的并发程序 也可以成为微线程 是一种新的多任务并发的操作手段
 *
 *  协成依赖于线程 但是协成挂起时不需要阻塞线程 几乎无代价
 *  协成由开发控制 所以协成也像用户态的线程 轻量级 一个线程中可以创建任意个协成
 *  Coroutine是编译器级的，Process和Thread是操作系统级的
 *
 *  Process和Thread是由os实现
 *  Coroutine是编译期通过相关的代码使得代码段能够实现分段式的执行
 *  缺点：本质是单线程 不能利用到单个CPU的多核
 *
 *  Thread用于独立的栈、局部变量，基于进程的共享内存
 *  但是多线程时 同一对象读写操作需要加锁来保证同步 加锁过多容易出现死锁
 *  线程之间调度由内核控制 对于开发是失控的 sleep、wait、yield的底层都是内核实现
 *
 *  Coroutine是跑在线程上的优化产物 拥有自己的栈内存和局部变量 共享成员变量
 *  传统Thread执行的核心是一个while(true)函数 本质是一个耗时函数
 *
 *  Coroutine可以用哪个来直接标记方法 由开发自己实现切换、调度
 *  一个线程上可以同时跑多个协成 同一事件只有一个协成被执行 在单线程上模拟多线程并发
 *  什么时候运行 什么时候暂停 都有开发决定
 *
 *  优点：省去线程切换、线程状态、数据并发开销 提高并发性能 只需要判断协成状态  效率高
 *       协成是非组塞的(有阻塞API) 不会阻塞当前线程 当前线程会去执行其他协成任务
 *
 *  协成表现为标记、切换方法、代码段
 *  使用suspend关键字修饰方法 即该方法可以被协成挂起 没用suspend修饰的方法不能参与协成任务
 *  suspend修饰的方法只能在协成中与另一个suspend修饰的方法调用
 */