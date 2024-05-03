
package com.meriem.casavia.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Code {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String code;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    public Code(String code, User user) {

        this.code = code;
        this.user = user;
    }





}