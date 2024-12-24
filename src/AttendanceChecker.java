package finalexam;

import java.io.*;
import java.util.*;

/**
 * �⼮ üũ ���α׷�
 * �л� ����� ���Ͽ��� �а�, �й��� �̸��� �Է¹޾� �⼮�� üũ�մϴ�.
 * �⼮ ������ ���Ͽ� ����˴ϴ�.
 * 
 * @version 1.0
 */
public class AttendanceChecker {
    private static long startTime;
    private static HashMap<String, String> attendanceMap = new HashMap<>();
    private static HashMap<String, String> studentMap = new HashMap<>();

    /**
     * ���α׷��� ������
     * @param args ����� �μ�
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        loadStudentData("D://temp/src/student_list.txt");

        String id;
        String name;
        
        while (true) {
            System.out.print("�й��� �Է��ϼ��� : ");
            id = in.nextLine();
            
            System.out.print("�̸��� �Է��ϼ��� : ");
            name = in.nextLine();

            if (studentMap.containsKey(id) && studentMap.get(id).equals(name)) {
                break;
            } else {
                System.out.println("�й��� �̸��� �ٽ� �� �� Ȯ���� �ּ���");
            }
        }

        String filePath = "D://temp/src/only_proffessor.txt";
        String expectedText = readTextFromFile(filePath);
        
        String inputText;
        
        startTime = System.currentTimeMillis();
        do {
            System.out.print("�ڵ带 �Է��ϼ��� : ");
            inputText = in.nextLine();
            
            long elapsedTime = System.currentTimeMillis() - startTime;
            
            if (inputText.equals(expectedText)) {
                if (elapsedTime <= 300000) {
                    System.out.println("�⼮ �Ϸ�!");
                    attendanceMap.put(id, "V");
                } else if (elapsedTime <= 3600000) {
                    System.out.println("�����Դϴ�");
                    attendanceMap.put(id, "|");
                } else {
                    System.out.println("�����Դϴ�");
                    attendanceMap.put(id, "X");
                }
            } else {
                System.out.println("�߸��� �ڵ��Դϴ�. �Է��� �ڵ带 �ٽ� Ȯ���� �ּ���");
            }
        } while (!inputText.equals(expectedText));
        
        saveAttendanceToFile("D://temp/src/attendance_check.txt", id, name);

        in.close();
    }

    /**
     * ���Ͽ��� �ؽ�Ʈ�� �о���� �޼���
     * @param filePath �о�� ���� ���
     * @return ������ ����
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
     * �⼮ ������ ���Ͽ� �����ϴ� �޼���
     * @param filePath ������ ���� ���
     * @param id �л��� �й�
     * @param name �л��� �̸�
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
     * �л� ��� ���Ͽ��� �����͸� �о� HashMap�� �����ϴ� �޼���
     * @param filePath �л� ��� ���� ���
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
