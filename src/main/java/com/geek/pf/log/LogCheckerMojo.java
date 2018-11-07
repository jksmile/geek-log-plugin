package com.geek.pf.log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.geek.pf.log.format.MessageFormat;
import com.geek.pf.log.validator.MessageValidator;

/**
 * Log checker.
 *
 * @author jinkai.xu
 * @date 2018/09/01
 */
@Mojo(name = "logChecker", defaultPhase = LifecyclePhase.COMPILE)
public class LogCheckerMojo extends AbstractMojo {

    private static final String PATTERN = "<messageid=\"(.*?)\"text=\"(.*?)\"level=\"(.*?)\"/>";

    private static final String LOG_DIR = "src/main/resources/META-INF/log";

    private static final String ERR_DIR = "src/main/resources/META-INF/error";

    private static final String LOG_SUFFIX = "log.xml";

    private static final String ERR_SUFFIX = "error.xml";

    private Log LOG = getLog();

    @Parameter( defaultValue = "${project.build.directory}", property = "sourceDir", required = true )
    private String sourceDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        String projectDir = sourceDir.substring(0, sourceDir.length() - 6);

        LOG.info("Checking project log format. project_dir:" + projectDir);

        verifyMsg(projectDir + LOG_DIR, LOG_SUFFIX);

        verifyMsg(projectDir + ERR_DIR, ERR_SUFFIX);
    }

    private void verifyMsg(String dir, String suffix) {

        File[] files = new File(dir).listFiles();

        if (null == files) {

            return;
        }

        for (File file : files) {

            if (file.getName().endsWith(suffix)) {

                try {

                    FileReader fileReader = new FileReader(file.getPath());

                    verifyMsg(fileReader);

                } catch (IOException e) {

                    e.printStackTrace();
                }

            }

        }
    }

    private void verifyMsg(FileReader reader) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        int ch;

        while ((ch = reader.read()) != -1) {

            stringBuilder.append((char) ch);
        }

        Pattern p = Pattern.compile(PATTERN);

        String xml = stringBuilder.toString()
                .replace("\n", "")
                .replace("\r", "")
                .replace(" ", "");

        Matcher matcher = p.matcher(xml);

        while (matcher.find()) {

            MessageFormat MessageFormat =
                    new MessageFormat(matcher.group(1), matcher.group(2), matcher.group(3), null);

            String res = MessageValidator.validate(MessageFormat);

            if (null != res) {

                LOG.error("ID" + MessageFormat.getId() + " errorMsg:" + res);

                System.exit(0);
            }

            LOG.info("[Processing] ID:" + matcher.group(1) + " OK");

        }
    }

}
