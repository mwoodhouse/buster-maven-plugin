package org.mw.buster;

import org.apache.maven.plugin.logging.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusterServerProcessExecutor {

    private Log log;

    private PhantomJsBrowser browser;
    private ProcessBuilder pb;
    private Process process;

    public BusterServerProcessExecutor(Log log) {
        this.log = log;

        pb = new ProcessBuilder("buster", "server");
    }

    public BusterServerProcessExecutor start(){
        log.info("STARTING SERVER");
        try {
            process = pb.start();
        } catch (IOException e) {
            log.error("Starting buster failed", e);
        } finally {
            return  this;
        }
    }

    public BusterServerProcessExecutor stop() {
        log.info("STOPPING SERVER");
        process.destroy();
        browser.stop();
        return this;
    }

    public BusterServerProcessExecutor captureBrowser() throws IOException {
        browser = new PhantomJsBrowser();
        browser.capturePhantomBrowser(getBusterUrl(process));
        return this;
    }

    public String getBusterUrl(Process process) throws IOException {
        final BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String busterUrl = null;
        String line;
        for (line = inputReader.readLine(); line != null; line = inputReader.readLine())
        {
            busterUrl = matchUrl(line);
            if(busterUrl != null)
                return busterUrl;
        }
        return busterUrl;
    }

    protected String matchUrl(String line){
        String re1=".*?";	// Non-greedy match on filler
        String re2="((?:http|https)(?::\\/{2}[\\w]+)(?:[\\/|\\.]?)(?:[^\\s\"]*))";	// HTTP URL 1

        Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(line);
        if (m.find())
        {
            return m.group(1);
        }
        return null;
    }

}
