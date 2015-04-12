import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.{Date, Calendar}

object CrawlerUtil {
  val day = new SimpleDateFormat("yyyy-MM-dd")
  val minute = new SimpleDateFormat("yyyy-MM-dd HH:mm")
  val second = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def converseTime(time: String): Timestamp = {
    val cal = Calendar.getInstance
    val minutes = cal.get(Calendar.MINUTE)
    val hours = cal.get(Calendar.HOUR_OF_DAY)
    val month = cal.get(Calendar.MONTH) + 1
    val day = cal.get(Calendar.DAY_OF_MONTH)
    time match {
      case c1 if c1.contains("分钟") =>
        val passMinutes = Integer.parseInt(time.substring(0, time.indexOf("分钟")))
        var realMinutes = minutes - passMinutes
        var realHours = hours
        if (realMinutes < passMinutes) {
          realHours -= 1
          realMinutes = 60 + realMinutes - passMinutes
        }
        val result = (cal.get(Calendar.YEAR) + "-" + (if (month < 10) "0" + month else month)
          + "-" + (if (day < 10) "0" + day else day) +
          " " + (if (realHours < 10) "0" + realHours else realHours) + ":" +
          (if (minutes < 10) "0" + minutes else minutes))
        new Timestamp(minute.parseObject(result).asInstanceOf[Date].getTime)

      case c2 if c2.contains("今天") =>
        val result = (cal.get(Calendar.YEAR) + "-" + (if (month < 10) "0" + month else month)
          + "-" + (if (day < 10) "0" + day else day) + " " + time.substring(3))
        new Timestamp(minute.parseObject(result).asInstanceOf[Date].getTime)

      case c3 if c3.contains("月") =>
        val realMonth = time.substring(0, time.indexOf("月"))
        val realDay = time.substring(time.indexOf("月") + 1,
          time.indexOf("日"))
        val hoursAndMi = time.substring(time.indexOf(" ") + 1
        )
        val cal = Calendar.getInstance()
        val result = cal.get(Calendar.YEAR) + "-" + realMonth +
          "-" + realDay + " " + hoursAndMi
        try {
          new Timestamp(minute.parseObject(result).asInstanceOf[Date].getTime)
        } catch {
          case x: Exception => println(result); new Timestamp(new Date().getTime)
        }

      case c4 => new Timestamp(second.parseObject(c4).asInstanceOf[Date].getTime)
    }
  }
}
