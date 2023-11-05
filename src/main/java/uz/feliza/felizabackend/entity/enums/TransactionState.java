package uz.feliza.felizabackend.entity.enums;

public enum TransactionState {
    /**
     * Transaction birinchi bo'lgan vaqtida uni holati NEW bo'lib turadi
     */
    NEW(0),

    /**
     * Pul o'tmay qolgan holatlar bo'ladi. Masalan, svet o'chib qoladi yoki internet ishlamay qoladi
     * Shunday holatlarda va qo'shimcha applicationdagi qanaqadur talablar bo'lsa uni holati
     * IN_PROGRESS ga o'zgartirilib qo'yiladi. Chunki jarayon oxirigacha yetmagan hisoblanadi
     */
    IN_PROGRESS(1),

    /**
     * Transaction o'tib bo'ldi hammasi yaxshi bunnda uni holati DONE bo'ladi
     */
    DONE(2),


    /**
     * Transaction bekor qilingandagi holati
     */
    CANCELLED(-1),

    POST_CANCELLED(-2);

    private int code;

    TransactionState(int code){
       this. code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
