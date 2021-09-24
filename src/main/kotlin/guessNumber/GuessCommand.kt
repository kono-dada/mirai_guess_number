package guessNumber

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.event.broadcast
import net.mamoe.mirai.event.events.MessageEvent

object GuessCommand : SimpleCommand(
    GuessNumber,
    "g"
) {
    @Handler
    suspend fun CommandSender.guess(number: String) {
        val numberList = number.toList().map { it.toInt() - 48 }
        if (numberList.size == 4)
            GameEvent(user!!, numberList).broadcast()
    }
}

object JoinCommand : SimpleCommand(
    GuessNumber,
    "猜数字"
) {
    @Handler
    suspend fun CommandSenderOnMessage<MessageEvent>.join() {
        JoinEvent(fromEvent.sender, fromEvent.subject).broadcast()
    }
}

object QuitCommand:SimpleCommand(
    GuessNumber,
    "退出猜数字"
){
    @Handler
    suspend fun CommandSender.quit(){
        QuitEvent(user!!).broadcast()
    }
}