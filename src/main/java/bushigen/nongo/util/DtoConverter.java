package bushigen.nongo.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * EntityからDTOへ変換するHelperクラス
 */
public class DtoConverter {

    /**
     * 単体のEntityをDTOクラスに変換
     *
     * @param entity 対象Entity
     * @param converter 変換するための関数
     * @param <T> the entity type
     * @param <R> the DTO type
     */
    public static <T, R> R convert(T entity, Function<T, R> converter) {
        if (entity == null) {
            return null;
        }

        // execute conversion function on the entity
        return converter.apply(entity);
    }

    /**
     * 複数のEntityをDTOクラスに変換
     *
     * @param entities 対象Entityの配列
     * @param converter 変換するための関数
     * @param <T> the entity type
     * @param <R> the DTO type
     */
    public static <T, R> List<R> convertList(List<T> entities, Function<T, R> converter) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
            .map(converter)
            .collect(Collectors.toList());
    }
}
