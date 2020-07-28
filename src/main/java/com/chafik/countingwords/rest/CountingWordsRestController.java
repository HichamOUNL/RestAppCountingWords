package com.chafik.countingwords.rest;

import com.chafik.countingwords.wordFrequency.WordFrequency;
import com.chafik.countingwords.wordFrequency.WordFrequencyAnalyzerImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class CountingWordsRestController {

    @Autowired
    WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    @ApiOperation(value = "Return the highest frequency in the text. Returns -1 when text is empty ")
    @GetMapping("/calculateHighestFrequency")
    public ResponseEntity<Object> calculateHighestFrequency(@RequestParam("text") String text) {
        try {
            return ResponseEntity.ok(wordFrequencyAnalyzer.calculateHighestFrequency(text));
        } catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @ApiOperation(value = "Return the frequency of the specified word in the text. Returns -1 when text or word is empty")
    @GetMapping("/calculateFrequencyForWord")
    public ResponseEntity<Object> calculateFrequencyForWord(@RequestParam("text") String text, @RequestParam("word") String word) {
        try {
            return ResponseEntity.ok(wordFrequencyAnalyzer.calculateFrequencyForWord(text, word));
        } catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }

    @ApiOperation("Return a list of the most frequent n words in the input text. Returns -1 when text is empty or number <= 0")
    @GetMapping("/calculateMostFrequentNWords")
    public ResponseEntity<Object> calculateMostFrequentNWords(@RequestParam("text") String text, @RequestParam("number") int number) {
        try {
            List<WordFrequency> words = wordFrequencyAnalyzer.calculateMostFrequentNWords(text, number);
            if (words != null) {
                String convertedWords = wordFrequencyAnalyzer.calculateMostFrequentNWordsAsList(words);
                return ResponseEntity.ok(convertedWords);
            } else {
                return ResponseEntity.ok(-1);
            }
        } catch (Exception exc) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }
    }
}
