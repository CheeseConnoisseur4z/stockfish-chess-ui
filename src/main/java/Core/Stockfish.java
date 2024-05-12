package Core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Stockfish
{
    private Process process;
    private BufferedReader reader;
    private OutputStreamWriter writer;

    private static String PATH = "\"C:\\Users\\tomsa\\git\\tom\\seminar_chess\\src\\stockfish\\stockfish-windows-x86-64-avx2.exe\"";


    public void startProcess() {
        try {
            process = Runtime.getRuntime().exec(PATH);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new OutputStreamWriter(process.getOutputStream());
        } catch (Exception e) {
            System.err.println("unable to start Stockfish");
        }
    }


    public void sendCommand(String command) {
        try {
            writer.write(command + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getBestMove(String fen, int moveTime) {
        sendCommand("position fen " + fen);
        sendCommand("go movetime " + moveTime);
        StringBuilder buffer = new StringBuilder();
        int c = 0;
        try {
            Thread.sleep(moveTime + 5);
            sendCommand("stop");
            sendCommand("isready");
            while (true) {
                String text = reader.readLine();
                if (text.equals("readyok"))
                    break;
                else {
                    buffer.append(text).append("\n");
                    c++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString().split("\n")[c - 2].split(" pv ")[1].split(" ")[0];
    }


    public void suicide() {
        try {
            sendCommand("quit");
            reader.close();
            writer.close();
        } catch (Exception ignored) {}
    }
}
