package guessNumber

import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.permission.AbstractPermitteeId
import net.mamoe.mirai.console.permission.PermissionService.Companion.permit
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.EventPriority
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.utils.info
import kotlin.coroutines.EmptyCoroutineContext

object GuessNumber : KotlinPlugin(
    JvmPluginDescription(
        id = "dada.guessNumber",
        version = "1.0-SNAPSHOT",
    )
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }

        GuessCommand.register()
        JoinCommand.register()
        QuitCommand.register()

        AbstractPermitteeId.AnyUser.permit(GuessCommand.permission)
        AbstractPermitteeId.AnyUser.permit(JoinCommand.permission)
        AbstractPermitteeId.AnyUser.permit(QuitCommand.permission)

        globalEventChannel().subscribeAlways<JoinEvent>(priority = EventPriority.MONITOR) {
            Game(EmptyCoroutineContext, ply, this.subject).start()
        }
    }
}