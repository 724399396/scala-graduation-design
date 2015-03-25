object CookieKeeper {
  private val cookie1 = Map("_T_WM" -> "fb858ad81bdf27cb4f37e30e4057efc8",
    "SUB" -> "_2A2550L8DDeTxGeNN6VcT9SnNzTuIHXVbOsFLrDV6PUJbrdBeLUKlkW0dK-8DzsGoflbIgtqd2n0zYLUrxg..",
    "gsid_CTandWM" -> "4uH6e7771dx5SSSR2uaUKmllf2H",
    "SUHB" -> "0y0JjkuwY3TSzK")
  // 181212631@163.com----wwee13
  private val cookie2 = Map("_T_WM" -> "7616333e60ca1d7afacd4331afa6ebf2",
    "SUB" -> "_2A2550KBJDeTxGeNN71QZ9yjKyz6IHXVbOsABrDV6PUJbrdBeLVTgkW2iEEGO6rzeZ5RGOGM0MZCRFzEfjA..",
    "gsid_CTandWM" -> "4urQe7771aCyibvPnbnhdmqXW9I",
    "SUHB" -> "0TPFn8pokoLSVn")
  // dayong213@163.com----wwee13----騳世木每的窝
  private val cookie3 = Map("_T_WM" -> "cbe9d479ea25fd43743b99991708da7a",
    "SUB" -> "_2A254A6oKDeTxGeNN6FQQ8ynPyjuIHXVbDzZCrDV6PUJbrdANLXj5kW0430AfU-f6L5A-AZ5UcRIZLfsmTA..",
    "gsid_CTandWM" -> "4uv43fd81Lbfiu3CzFxcdmoaJ57")
  // ougandun9159640zha@163.com----qwer1234
  val allCookies = {
    val l = new LoopList[Map[String, String]]();
    l.add(cookie1);
    l.add(cookie2);
    l.add(cookie3);
    l
  }
}

class LoopList[T] {

  class Node[T](val e: T, var next: Node[T] = null) {

  }

  var head: Node[T] = _
  var tail: Node[T] = _
  var current: Node[T] = _
  var first = true

  def add(a: T) {
    val temp = new Node(a)
    if (first) {
      first = false;
      head = temp;
      tail = temp;
      current = temp
    } else {
      tail.next = temp;
      tail = temp;
      tail.next = head
    }
  }

  def next(): T = {
    val r = current.e
    current = current.next
    r
  }
}

