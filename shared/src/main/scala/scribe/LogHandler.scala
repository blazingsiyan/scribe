package scribe

import scribe.format.Formatter
import scribe.writer.{ConsoleWriter, Writer}

trait LogHandler {
  def level: Level

  final def log(record: LogRecord): Unit = if (accepts(record.value)) {
    publish(record)
  }

  protected def publish(record: LogRecord): Unit

  def accepts(value: Double): Boolean = value >= level.value
}

case class StandardLogHandler(level: Level, formatter: Formatter, writer: Writer) extends LogHandler {
  override protected def publish(record: LogRecord): Unit = {
    writer.write(record, formatter)
  }
}

object LogHandler {
  def apply(level: Level = Level.Info,
            formatter: Formatter = Formatter.default,
            writer: Writer = ConsoleWriter): LogHandler = {
    StandardLogHandler(level, formatter, writer)
  }
}