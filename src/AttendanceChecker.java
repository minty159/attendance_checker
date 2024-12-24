package finalexam;

import java.io.*;
import java.util.*;

/**
 * 출석 체크 프로그램
 * 학생 목록을 파일에서 읽고, 학번과 이름을 입력받아 출석을 체크합니다.
 * 출석 정보는 파일에 저장됩니다.
 * 
 * @version 1.0
 */
public class AttendanceChecker {
    private static long startTime;
    private static HashMap<String, String> attendanceMap = new HashMap<>();
    private static HashMap<String, String> studentMap = new HashMap<>();

    /**
     * 프로그램의 시작점
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        loadStudentData("D://temp/src/student_list.txt");

        String id;
        String name;
        
        while (true) {
            System.out.print("학번을 입력하세요 : ");
            id = in.nextLine();
            
            System.out.print("이름을 입력하세요 : ");
            name = in.nextLine();

            if (studentMap.containsKey(id) && studentMap.get(id).equals(name)) {
                break;
            } else {
                System.out.println("학번과 이름을 다시 한 번 확인해 주세요");
            }
        }

        String filePath = "D://temp/src/only_proffessor.txt";
        String expectedText = readTextFromFile(filePath);
        
        String inputText;
        
        startTime = System.currentTimeMillis();
        do {
            System.out.print("코드를 입력하세요 : ");
            inputText = in.nextLine();
            
            long elapsedTime = System.currentTimeMillis() - startTime;
            
            if (inputText.equals(expectedText)) {
                if (elapsedTime <= 300000) {
                    System.out.println("출석 완료!");
                    attendanceMap.put(id, "V");
                } else if (elapsedTime <= 3600000) {
                    System.out.println("지각입니다");
                    attendanceMap.put(id, "|");
                } else {
                    System.out.println("지각입니다");
                    attendanceMap.put(id, "X");
                }
            } else {
                System.out.println("잘못된 코드입니다. 입력한 코드를 다시 확인해 주세요");
            }
        } while (!inputText.equals(expectedText));
        
        saveAttendanceToFile("D://temp/src/attendance_check.txt", id, name);

        in.close();
    }

    /**
     * 파일에서 텍스트를 읽어오는 메서드
     * @param filePath 읽어올 파일 경로
     * @return 파일의 내용
     */
    private static String readTextFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().trim();
    }

    /**
     * 출석 정보를 파일에 저장하는 메서드
     * @param filePath 저장할 파일 경로
     * @param id 학생의 학번
     * @param name 학생의 이름
     */
    private static void saveAttendanceToFile(String filePath, String id, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(id + ", " + name + ", " + attendanceMap.get(id));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 학생 목록 파일에서 데이터를 읽어 HashMap에 저장하는 메서드
     * @param filePath 학생 목록 파일 경로
     */
    private static void loadStudentData(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    studentMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
