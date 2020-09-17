package io.snyk.gradle.plugin;

import org.gradle.api.GradleException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Runner {

    private Runner() {
    }

    public static Result runCommand(String task) {
        try {
            Process process = Runtime.getRuntime().exec(task);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            StringBuilder content = new StringBuilder();
            StringBuilder error = new StringBuilder();
            boolean hasError = false;
            while ((line = stdInput.readLine()) != null) {
                content.append("\n");
                content.append(line);
            }

            while ((line = stdError.readLine()) != null) {
                if (!hasError) hasError = true;
                content.append("\n");
                error.append(line);
            }

            process.waitFor();
            int exitStatus = process.exitValue();
            String result = content.toString() + (hasError ? "Error: " + error.toString() : "");
            return new Result(result, exitStatus);

        } catch (InterruptedException e) {
            throw new GradleException("Internal error", e);
        } catch (IOException e) {
            return new Result(e.getMessage(), 1);
        }
    }

    public static class Result {

        String output;
        int exitcode;

        public Result(String output, int exitcode) {
            this.output = output;
            this.exitcode = exitcode;
        }

        public String getOutput() {
            return output;
        }

        public int getExitcode() {
            return exitcode;
        }

        public boolean failed() {
            return exitcode != 0;
        }
    }
}
