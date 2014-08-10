package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

/**
 * Created by ishi on 2014/08/10.
 */
case class Task(id: Long, label: String, meaning: String)

object Task {

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

  def create(label: String, meaning: String) {
    val params = Seq[NamedParameter](
      'label -> label,
      'meaning -> meaning
    )
    DB.withConnection { implicit c =>
      SQL("insert into task (label, meaning) values ({label}, {meaning})").on(
        params:_*
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

  val task = {
    get[Long]("id") ~
      get[String]("label") ~
      get[String]("meaning") map {
      case id~label~meaning => Task(id, label, meaning)
    }
  }

}
