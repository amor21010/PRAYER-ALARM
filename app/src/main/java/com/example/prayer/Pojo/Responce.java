package com.example.prayer.Pojo;

import java.util.List;


@SuppressWarnings("all")
public class Responce {
    private final int code;

    private final String status;

    private final Data data;

    public Responce(int code, String status, Data data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        private final Timings timings;

        private final Date date;

        private final Meta meta;

        public Data(Timings timings, Date date, Meta meta) {
            this.timings = timings;
            this.date = date;
            this.meta = meta;
        }

        public Timings getTimings() {
            return timings;
        }

        public Date getDate() {
            return date;
        }

        public Meta getMeta() {
            return meta;
        }

        public static class Timings {
            private final String Fajr;

            private final String Sunrise;

            private final String Dhuhr;

            private final String Asr;

            private final String Sunset;

            private final String Maghrib;

            private final String Isha;

            private final String Imsak;

            public Timings(String fajr, String sunrise, String dhuhr, String asr, String sunset, String maghrib, String isha, String imsak) {
                Fajr = fajr;
                Sunrise = sunrise;
                Dhuhr = dhuhr;
                Asr = asr;
                Sunset = sunset;
                Maghrib = maghrib;
                Isha = isha;
                Imsak = imsak;
            }

            public String getFajr() {
                return Fajr;
            }

            public String getSunrise() {
                return Sunrise;
            }

            public String getDhuhr() {
                return Dhuhr;
            }

            public String getAsr() {
                return Asr;
            }

            public String getSunset() {
                return Sunset;
            }

            public String getMaghrib() {
                return Maghrib;
            }

            public String getIsha() {
                return Isha;
            }

            public String getImsak() {
                return Imsak;
            }
        }

        public static class Date {
            private final String readable;

            private final String timestamp;

            private final Hijri hijri;

            private final Gregorian gregorian;

            public Date(String readable, String timestamp, Hijri hijri, Gregorian gregorian) {
                this.readable = readable;
                this.timestamp = timestamp;
                this.hijri = hijri;
                this.gregorian = gregorian;
            }

            public String getReadable() {
                return readable;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public Hijri getHijri() {
                return hijri;
            }

            public Gregorian getGregorian() {
                return gregorian;
            }

            public static class Hijri {
                private final String date;

                private final String format;

                private final String day;

                private final Weekday weekday;

                private final Month month;

                private final String year;

                private final Designation designation;

                private final List<Object> holidays;

                public Hijri(String date, String format, String day, Weekday weekday, Month month,
                             String year, Designation designation, List<Object> holidays) {
                    this.date = date;
                    this.format = format;
                    this.day = day;
                    this.weekday = weekday;
                    this.month = month;
                    this.year = year;
                    this.designation = designation;
                    this.holidays = holidays;
                }

                public String getDate() {
                    return date;
                }

                public String getFormat() {
                    return format;
                }

                public String getDay() {
                    return day;
                }

                public Weekday getWeekday() {
                    return weekday;
                }

                public Month getMonth() {
                    return month;
                }

                public String getYear() {
                    return year;
                }

                public Designation getDesignation() {
                    return designation;
                }

                public List<Object> getHolidays() {
                    return holidays;
                }

                public static class Weekday {
                    private final String en;

                    private final String ar;

                    public Weekday(String en, String ar) {
                        this.en = en;
                        this.ar = ar;
                    }

                    public String getEn() {
                        return en;
                    }

                    public String getAr() {
                        return ar;
                    }
                }

                public static class Month {
                    private final int number;

                    private final String en;

                    private final String ar;

                    public Month(int number, String en, String ar) {
                        this.number = number;
                        this.en = en;
                        this.ar = ar;
                    }

                    public int getNumber() {
                        return number;
                    }

                    public String getEn() {
                        return en;
                    }

                    public String getAr() {
                        return ar;
                    }
                }

                public static class Designation {
                    private final String abbreviated;

                    private final String expanded;

                    public Designation(String abbreviated, String expanded) {
                        this.abbreviated = abbreviated;
                        this.expanded = expanded;
                    }

                    public String getAbbreviated() {
                        return abbreviated;
                    }

                    public String getExpanded() {
                        return expanded;
                    }
                }
            }

            public static class Gregorian {
                private final String date;

                private final String format;

                private final String day;

                private final Weekday weekday;

                private final Month month;

                private final String year;

                private final Designation designation;

                public Gregorian(String date, String format, String day, Weekday weekday,
                                 Month month, String year, Designation designation) {
                    this.date = date;
                    this.format = format;
                    this.day = day;
                    this.weekday = weekday;
                    this.month = month;
                    this.year = year;
                    this.designation = designation;
                }

                public String getDate() {
                    return date;
                }

                public String getFormat() {
                    return format;
                }

                public String getDay() {
                    return day;
                }

                public Weekday getWeekday() {
                    return weekday;
                }

                public Month getMonth() {
                    return month;
                }

                public String getYear() {
                    return year;
                }

                public Designation getDesignation() {
                    return designation;
                }

                public static class Weekday {
                    private final String en;

                    public Weekday(String en) {
                        this.en = en;
                    }

                    public String getEn() {
                        return en;
                    }
                }

                public static class Month {
                    private final int number;

                    private final String en;

                    public Month(int number, String en) {
                        this.number = number;
                        this.en = en;
                    }

                    public int getNumber() {
                        return number;
                    }

                    public String getEn() {
                        return en;
                    }
                }

                public static class Designation {
                    private final String abbreviated;

                    private final String expanded;

                    public Designation(String abbreviated, String expanded) {
                        this.abbreviated = abbreviated;
                        this.expanded = expanded;
                    }

                    public String getAbbreviated() {
                        return abbreviated;
                    }

                    public String getExpanded() {
                        return expanded;
                    }
                }
            }
        }

        public static class Meta {
            private final double latitude;

            private final double longitude;

            private final String timezone;

            private final Method method;

            private final String latitudeAdjustmentMethod;

            private final String midnightMode;

            private final String school;

            private final Offset offset;

            public Meta(double latitude, double longitude, String timezone, Method method,
                        String latitudeAdjustmentMethod, String midnightMode, String school,
                        Offset offset) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.timezone = timezone;
                this.method = method;
                this.latitudeAdjustmentMethod = latitudeAdjustmentMethod;
                this.midnightMode = midnightMode;
                this.school = school;
                this.offset = offset;
            }

            public double getLatitude() {
                return latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public String getTimezone() {
                return timezone;
            }

            public Method getMethod() {
                return method;
            }

            public String getLatitudeAdjustmentMethod() {
                return latitudeAdjustmentMethod;
            }

            public String getMidnightMode() {
                return midnightMode;
            }

            public String getSchool() {
                return school;
            }

            public Offset getOffset() {
                return offset;
            }

            public static class Method {
                private final int id;

                private final String name;

                private final Params params;

                public Method(int id, String name, Params params) {
                    this.id = id;
                    this.name = name;
                    this.params = params;
                }

                public int getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public Params getParams() {
                    return params;
                }

                public static class Params {
                    private final int fajr;

                    private final int isha;

                    public Params(int fajr, int isha) {
                        this.fajr = fajr;
                        this.isha = isha;
                    }

                    public int getFajr() {
                        return fajr;
                    }

                    public int getIsha() {
                        return isha;
                    }
                }
            }

            public static class Offset {
                private final int imsak;

                private final int fajr;

                private final int sunrise;

                private final int dhuhr;

                private final int asr;

                private final int maghrib;

                private final int sunset;

                private final int isha;

                private final int midnight;

                public Offset(int imsak, int fajr, int sunrise, int dhuhr, int asr, int maghrib,
                              int sunset, int isha, int midnight) {
                    this.imsak = imsak;
                    this.fajr = fajr;
                    this.sunrise = sunrise;
                    this.dhuhr = dhuhr;
                    this.asr = asr;
                    this.maghrib = maghrib;
                    this.sunset = sunset;
                    this.isha = isha;
                    this.midnight = midnight;
                }

                public int getImsak() {
                    return imsak;
                }

                public int getFajr() {
                    return fajr;
                }

                public int getSunrise() {
                    return sunrise;
                }

                public int getDhuhr() {
                    return dhuhr;
                }

                public int getAsr() {
                    return asr;
                }

                public int getMaghrib() {
                    return maghrib;
                }

                public int getSunset() {
                    return sunset;
                }

                public int getIsha() {
                    return isha;
                }

                public int getMidnight() {
                    return midnight;
                }
            }
        }
    }
}
