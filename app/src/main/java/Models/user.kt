package Models

class user {
    var Image:String?=null;
    var Name:String?=null;
    var Password:String?=null;
    var Email:String?=null;
    var Gender:String?=null;
    var Date_of_birth:String?=null;
    var Age:String?=null;
    var Phone_number:String?=null;

    constructor()
        constructor(Password: String?, Email: String?) {
        this.Email = Email
        this.Password = Password

    }

    constructor(
        Image: String?,
        Name: String?,
        Gender: String?,
        Date_of_birth: String?,
        Phone_number: String?,
    ) {
        this.Image = Image
        this.Name = Name
        this.Gender = Gender
        this.Date_of_birth = Date_of_birth
        this.Phone_number = Phone_number
    }


}