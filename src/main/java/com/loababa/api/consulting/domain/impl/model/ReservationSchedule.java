package com.loababa.api.consulting.domain.impl.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public record ReservationSchedule(
        @Schema(
                description = """
                        {
                          "code": "OK",
                          "data": {
                            "value": {
                              "2024-08-25": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-25T16:00:00",
                                    "endDateTime": "2024-08-25T17:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-08-25T17:00:00",
                                    "endDateTime": "2024-08-25T18:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-08-26": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-26T08:00:00",
                                    "endDateTime": "2024-08-26T09:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-08-27": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-27T11:00:00",
                                    "endDateTime": "2024-08-27T12:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-08-27T12:00:00",
                                    "endDateTime": "2024-08-27T13:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-08-28": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-28T13:00:00",
                                    "endDateTime": "2024-08-28T14:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-08-29": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-29T10:00:00",
                                    "endDateTime": "2024-08-29T11:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-08-30": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-30T14:00:00",
                                    "endDateTime": "2024-08-30T15:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-08-30T15:00:00",
                                    "endDateTime": "2024-08-30T16:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-08-31": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-08-31T12:00:00",
                                    "endDateTime": "2024-08-31T13:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-08-31T13:00:00",
                                    "endDateTime": "2024-08-31T14:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-01": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-01T16:00:00",
                                    "endDateTime": "2024-09-01T17:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-09-01T17:00:00",
                                    "endDateTime": "2024-09-01T18:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-02": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-02T08:00:00",
                                    "endDateTime": "2024-09-02T09:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-03": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-03T11:00:00",
                                    "endDateTime": "2024-09-03T12:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-09-03T12:00:00",
                                    "endDateTime": "2024-09-03T13:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-04": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-04T13:00:00",
                                    "endDateTime": "2024-09-04T14:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-05": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-05T10:00:00",
                                    "endDateTime": "2024-09-05T11:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-06": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-06T14:00:00",
                                    "endDateTime": "2024-09-06T15:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-09-06T15:00:00",
                                    "endDateTime": "2024-09-06T16:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              },
                              "2024-09-07": {
                                "isBusinessDay": true,
                                "slots": [
                                  {
                                    "startDateTime": "2024-09-07T12:00:00",
                                    "endDateTime": "2024-09-07T13:00:00",
                                    "isAvailable": true
                                  },
                                  {
                                    "startDateTime": "2024-09-07T13:00:00",
                                    "endDateTime": "2024-09-07T14:00:00",
                                    "isAvailable": true
                                  }
                                ]
                              }
                            }
                          }
                        }
                        """
        )
        Map<LocalDate, DaySchedule> value
) {

    public ReservationSchedule() {
        this(new LinkedHashMap<>());
    }

    public void put(LocalDate date, DaySchedule daySchedule) {
        value.put(date, daySchedule);
    }

}
