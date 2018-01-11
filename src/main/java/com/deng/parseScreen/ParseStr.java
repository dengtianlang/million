package com.deng.parseScreen;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.segmentation.Segmentation;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.SegmentationFactory;
import org.apdplat.word.segmentation.Word;
import org.apdplat.word.util.WordConfTools;

import java.util.List;

/**
 * @Author dengtianlang@jd.com
 * @DateTime 2018/1/11 11:34
 * @Version
 */
public class ParseStr {

    Segmentation segmentation;

    public ParseStr(){
        WordConfTools.set("stopwords.path", "classpath:src/main/resources/stopword/stopword.dic");
        DictionaryFactory.reload();

        this.segmentation  = SegmentationFactory.getSegmentation(SegmentationAlgorithm.FullSegmentation);

    }

   public String parseStr(String originStr){


        StringBuilder result = new StringBuilder();
       for(Word word : this.segmentation.seg(originStr)){
           result.append(word.getText()).append(" ");
       }
       return result.toString();
   }

    public Segmentation getSegmentation() {
        return segmentation;
    }

    public void setSegmentation(Segmentation segmentation) {
        this.segmentation = segmentation;
    }
}
