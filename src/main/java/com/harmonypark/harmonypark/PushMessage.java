package com.harmonypark.harmonypark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushMessage {
    private String text;

    private String to;

}
