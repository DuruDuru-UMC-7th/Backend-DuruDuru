package com.backend.DuruDuru.global.service.IngredientService;

public class DateFormatter {

    public static String formatRemainingDays(long dDay) {
        if (dDay < 30) {
            return dDay + "일";
        } else if (dDay < 60) {
            return "2개월";
        } else if (dDay < 90) {
            return "3개월";
        } else if (dDay < 365) {
            int months = (int) (dDay / 30);
            return months + "개월";
        } else {
            int years = (int) (dDay / 365);
            int remainingDays = (int) (dDay % 365);
            int months = remainingDays / 30;
            return months == 0 ? years + "년" : years + "년 " + months + "개월";
        }
    }

    public static void main(String[] args) {
        System.out.println(formatRemainingDays(10));   // 10일
        System.out.println(formatRemainingDays(29));   // 29일
        System.out.println(formatRemainingDays(30));   // 2개월
        System.out.println(formatRemainingDays(59));   // 2개월
        System.out.println(formatRemainingDays(60));   // 3개월
        System.out.println(formatRemainingDays(89));   // 3개월
        System.out.println(formatRemainingDays(90));   // 3개월
        System.out.println(formatRemainingDays(120));  // 4개월
        System.out.println(formatRemainingDays(365));  // 1년
        System.out.println(formatRemainingDays(400));  // 1년 1개월
        System.out.println(formatRemainingDays(730));  // 2년
        System.out.println(formatRemainingDays(800));  // 2년 2개월
    }
}

