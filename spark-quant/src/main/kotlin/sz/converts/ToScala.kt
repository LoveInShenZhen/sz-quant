package sz.converts

//

import scala.collection.Seq
import scala.collection.JavaConverters

// Created by kk on 2018/11/21.
//
object ToScala {

    fun <T> toSeq(list: List<T>): Seq<T> {
        return JavaConverters.asScalaBufferConverter(list).asScala().toSeq()
    }

}

fun <T> List<T>.toScalaSeq() : Seq<T> {
    return ToScala.toSeq(this)
}


