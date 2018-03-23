package ragnarok;

import com.google.common.base.Joiner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.Bukkit;

public class Messaging {
    private static boolean LOGGING;
    private static Logger CASX_LOGGER;
    private static Logger LOGGER;
    private static final Joiner SPACE;

    public Messaging() {
    }

    public static void configure(File UniAucFile, boolean logging) {
        LOGGING = logging;
        if(Bukkit.getLogger() != null) {
            LOGGER = Bukkit.getLogger();
            CASX_LOGGER = LOGGER;
        }

        if(UniAucFile != null) {
            CASX_LOGGER = Logger.getLogger("CasinoX");
            try {
                FileHandler e = new FileHandler(UniAucFile.getAbsolutePath(), true);
                e.setFormatter(new Messaging.CasXLogFormatter());
                CASX_LOGGER.setUseParentHandlers(false);
                CASX_LOGGER.addHandler(e);
            } catch (SecurityException var5) {
                var5.printStackTrace();
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        }

    }

    public static void CasXlog(Object... msg) {
        if(isLogging()) {
            CASX_LOGGER.log(Level.INFO, SPACE.join(msg));
        }
    }

    public static boolean isLogging() {
        return LOGGING;
    }

    static {
        LOGGING = false;
        LOGGER = Logger.getLogger("CasinoX");
        SPACE = Joiner.on(" ").useForNull("null");
    }

    private static class CasXLogFormatter extends Formatter {
        private final SimpleDateFormat date;

        private CasXLogFormatter() {
            this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        }

        public String format(LogRecord rec) {
            Throwable exception = rec.getThrown();
            String out = this.date.format(Long.valueOf(rec.getMillis()));
            out = out + "[" + rec.getLevel().getName().toUpperCase() + "] ";
            out = out + rec.getMessage() + '\n';
            if(exception != null) {
                StringWriter writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));
                return out + writer;
            } else {
                return out;
            }
        }
    }
}
