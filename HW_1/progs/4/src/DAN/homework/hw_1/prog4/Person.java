package DAN.homework.hw_1.prog4;

public class Person {
    private final boolean man;
    private final String name;
    private Person spouse;

    public Person(boolean man, String name) {
        this.man = man;
        this.name = name;
    }

    /*
     *
     * This method checks gender of persons. If genders are not equal - tries to marry.
     * one of them has another spouse - execute divorce(sets spouse = null for husband and wife.
     * Example:if both persons have spouses - then divorce will set 4 spouse to null) and then executes marry().
     * @param person - new husband/wife for this person.
     * @return - returns true if this person has another gender than passed person and they are not husband
     * and wife, false otherwise
     */

    public boolean marry(Person person) {
        boolean result;
        if(man == person.man || person == spouse){
             result = false;
        }
        else{
            if(person.spouse != null){
                person.spouse.divorce();
            }
            person.divorce();
            if(spouse != null){
                spouse.divorce();
            }
            divorce();
            person.spouse = this;
            spouse = person;
            result = true;
        }
        return result;
    }

    /**
     * Sets spouse = null if spouse is not null
     *
     * @return true - if person status has been changed
     */
    public boolean divorce() {
        boolean result;
        if(spouse != null){
            spouse = null;
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }

    @Override
    public String toString(){
        return  name + (spouse == null ? " is a single" : " has " + (man ? " wife ": " husband ") + spouse.name);
    }
}


