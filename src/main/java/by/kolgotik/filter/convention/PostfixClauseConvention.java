package by.kolgotik.filter.convention;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostfixClauseConvention {

    private Set<String> defaultClauses = Set.of(
            "-min",
            "-max",
            "-asc",
            "-desc"
    );
}
