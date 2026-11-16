package top.ghhccghk.yadea.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/**
 * 应用内调试日志（设置页「调试」入口）。
 *
 * [top.ghhccghk.yadea.network.NetworkLog] 的诊断输出在开启记录后会同步镜像到这里，
 * 供设置页查看与复制；关闭时不留存新日志。内容仅保存在内存中，应用退出即清空；
 * 磁盘上的完整日志仍由 NetworkLog 写入 `yadea-logs/mirror-log.txt`。
 */
@OptIn(ExperimentalTime::class)
object DebugLog {
    private const val MAX_LINES = 1000

    var enabled by mutableStateOf(false)
        private set

    val lines = mutableStateListOf<String>()

    fun setEnabled(value: Boolean) {
        enabled = value
        if (value) {
            record("LOG 日志记录已开启")
        }
    }

    fun record(line: String) {
        if (!enabled) return
        lines.add("${Clock.System.now()} $line")
        while (lines.size > MAX_LINES) {
            lines.removeAt(0)
        }
    }

    fun clear() {
        lines.clear()
    }

    fun exportText(): String = lines.joinToString(separator = "\n")
}
