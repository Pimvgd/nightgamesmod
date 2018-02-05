package nightgames.global;

import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

public class Logwriter {
    private Logwriter() {
        
    }
    
    public static void setupExceptionHandler() {
        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stacktrace = sw.toString();
            System.err.println(stacktrace);
        });
    }

    public static void makeLogger(Date jdate) {
        PrintStream fstream;
        try {
            File logfile = new File("nightgames_log.txt");
            // append the log if it's less than 2 megs in size.
            fstream = new PrintStream(new FileOutputStream(logfile, logfile.length() < 2L * 1024L * 1024L));
            OutputStream estream = new TeeStream(System.err, fstream);
            OutputStream ostream = new TeeStream(System.out, fstream);
            System.setErr(new PrintStream(estream));
            System.setOut(new PrintStream(ostream));
    		ClassLoader loader = Thread.currentThread().getContextClassLoader();
    		InputStream stream = loader.getResourceAsStream("build.properties");

            System.out.println("=============================================");
            System.out.println("Nightgames Mod");
    		if (stream != null) {
    			Properties prop = new Properties();
    			prop.load(stream);
    			System.out.println("version: " + prop.getProperty("version"));
    			System.out.println("buildtime: " + prop.getProperty("buildtime"));
    			System.out.println("builder: " + prop.getProperty("builder"));
    		} else {
    			System.out.println("dev-build");
    		}
            System.out.println(new Timestamp(jdate.getTime()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
