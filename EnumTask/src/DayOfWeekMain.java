public class DayOfWeekMain {

    public static void main(String[] args) {
        System.out.println(workingHours(DayOfWeek.MONDAY));
    }

    static String workingHours(DayOfWeek day) {
        String message;
        if (day.ordinal() == 5 || day.ordinal() == 6){
            message = "Сегодня выходной";
        } else {
            int sum = 0;
            for (int i = day.ordinal(); i < 5; i++) {
                sum += day.getWorkingHours();
            }
            message = "Количество рабочих часов до конца недели = " + sum;
        }
        return message;
    }
}
