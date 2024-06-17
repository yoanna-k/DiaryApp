package com.example.f108179;

import java.util.List;

public class HolidayResponse {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        private List<Holiday> holidays;

        public List<Holiday> getHolidays() {
            return holidays;
        }
    }

    public static class Holiday {
        private String name;
        private Date date;

        public String getName() {
            return name;
        }

        public Date getDate() {
            return date;
        }

        public static class Date {
            private String iso;

            public String getIso() {
                return iso;
            }
        }
    }
}
