package com.bozo.app.pdf;

import com.bozo.app.bankNames.BankNames;
import com.bozo.app.utilities.implementations.ExtractingAccountAmountPairMilleniumImp;
import com.bozo.app.utilities.implementations.ExtractingAccountAmountPairPekaoImp;
import com.bozo.app.utilities.interfaces.CustomPair;
import com.bozo.app.utilities.interfaces.ExtractingAccountAmountPair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ExtractsAccountsAndAmounts {


    public List<CustomPair<String, BigDecimal>> getAccountsAndAmountsList(File[] files){
        List<CustomPair<String, BigDecimal>> accountsAndAmountsList = new ArrayList<>();
        for (File file : files){
            accountsAndAmountsList.add(checkBankName(file));
        }
        return Collections.unmodifiableList(accountsAndAmountsList);
    }

    private CustomPair<String, BigDecimal> checkBankName(File file){
        CustomPair<String, BigDecimal> pair = null;
        try(PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String text = pdfTextStripper.getText(document);
            String[] lines = text.split("\n");
            for (String line : lines){
                if (Pattern.compile("[a-zA-Z]* " + BankNames.PEKAO.name() + " S.A.\\s*").matcher(line.toUpperCase()).matches()){
                    pair = getCustomPair(lines, new ExtractingAccountAmountPairPekaoImp());
                    break;
                }
                if (Pattern.compile("[a-zA-Z]*\\s" + BankNames.MILLENNIUM.name() + " S A\\s*").matcher(line.toUpperCase()).matches()){
                    pair = getCustomPair(lines, new ExtractingAccountAmountPairMilleniumImp());
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return pair;
    }

    private CustomPair<String, BigDecimal> getCustomPair(String[] lines, ExtractingAccountAmountPair<String, BigDecimal> extracting){
        return extracting.getCustomPair(lines);
    }

}
