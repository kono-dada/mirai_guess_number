package guessNumber

import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.AbstractEvent

class GameEvent(val ply: User, val number: List<Int>) : AbstractEvent()

class JoinEvent(val ply: User, val subject:Contact) : AbstractEvent()

class QuitEvent(val ply: User) : AbstractEvent()