package bushigen.nongo.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * EntityからDTOへ変換するHelperクラス
 */
public class DtoConverter {

    /**
     * TをRに変換
     *
     * @param entity 対象Entity
     * @param converter 変換するための関数
     * @param <T> source object
     * @param <R> return object
     */
    public static <T, R> R convert(T source, Function<T, R> converter) {
        if (source == null) {
            return null;
        }

        // execute conversion function on the source
        return converter.apply(source);
    }

    /**
     * 複数のTをRのリストに変換
     *
     * @param sources 対象Entityの配列
     * @param converter 変換するための関数
     * @param <T> the entity type
     * @param <R> the DTO type
     */
    public static <T, R> List<R> convertList(List<T> sources, Function<T, R> converter) {
        if (sources == null) {
            return null;
        }

        return sources.stream()
            .map(converter)
            .collect(Collectors.toList());
    }
}
