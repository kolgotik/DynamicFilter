package by.kolgotik.filter.test;

import by.kolgotik.filter.annotation.ClientFilterName;
import by.kolgotik.filter.annotation.FilterObject;
import by.kolgotik.filter.annotation.FilterParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@FilterObject(tableName = "user")
public class UserFilter {

    @ClientFilterName(name = "username")
    @FilterParam(columnName = "user-name")
    private String name;

    @ClientFilterName(name = "age-min")
    @FilterParam(columnName = "user-age")
    private int age;

    @ClientFilterName(name = "age-max")
    @FilterParam(columnName = "user-age")
    private int ageMax;

    //@ClientFilterName(name = "pass")
    @FilterParam(columnName = "user-password")
    private String password;

    @ClientFilterName(name = "email")
    @FilterParam(columnName = "user-email")
    private String email;

}
