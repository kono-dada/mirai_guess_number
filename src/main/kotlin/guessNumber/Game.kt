package guessNumber

import dada.addPoints
import dada.pay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import kotlin.coroutines.CoroutineContext
import kotlin.math.pow

class Game(
    override val coroutineContext: CoroutineContext,
    private val player: User,
    private val subject: Contact
) :
    CoroutineScope {
    private val game = Job()

    private var round = 0
    private val maxRound = 5

    private val numberToBeGuessed = (0..9).shuffled().take(4)
    private val numberString = numberToBeGuessed.joinToString("")

    private val channel = globalEventChannel(game)

    suspend fun start() {
        GuessNumber.logger.info("生成了$numberToBeGuessed")
        sendMsg(
            "100明乃币我就收走了哦~猜数字开始~" +
                    "\n我想好了一个没有重复数字的四位数，快来猜猜看吧(剩余回合越多，奖励就越丰富哦)" +
                    "\n还剩${maxRound - round + 1}次机会"
        )
        player.pay(100)

        channel.subscribeAlways<QuitEvent> {
            if (ply == player) {
                sendMsg("退出成功")
                over()
            }
        }

        channel.subscribeAlways<JoinEvent> {
            if (ply == player) {
                sendMsg("你已经在游戏中了")
                intercept()
            }
        }

        channel.subscribeAlways<GameEvent> {
            if (ply == player) {
                var a = 0
                var b = 0
                for (i in (0..3)) {
                    if (number[i] == numberToBeGuessed[i]) a += 1
                    else if (number[i] in numberToBeGuessed) b += 1
                }

                if (a == 4) {
                    val bonus = (2.0.pow(maxRound - round) * 200).toInt()
                    sendMsg(
                        "恭喜你猜对了，答案就是${
                            number.joinToString(
                                prefix = "[",
                                postfix = "]"
                            )
                        }\n明乃币+$bonus"
                    )
                    over()
                    player.addPoints(bonus)
                } else {
                    if (round == maxRound) {
                        sendMsg("你没猜出来，答案是$numberString")
                        over()
                    } else {
                        sendMsg("${a}A${b}B\n(猜中${a}个位置正确的数，猜中${b}个包含但位置不正确的数)")
                        sendMsg("还剩${maxRound - round}次机会")
                        round += 1
                    }
                }
            }
        }
    }

    private fun over() {
        game.cancel()
    }

    private suspend fun sendMsg(message: String){
        if(subject is Group) subject.sendMessage(At(player) + PlainText(message))
        else subject.sendMessage(message)
    }
}
