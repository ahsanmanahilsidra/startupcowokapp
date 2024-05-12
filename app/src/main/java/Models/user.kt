package Models

class user {
    var userid:String?=""
    var Image:String?=null;
    var Name:String?=null;
    var Password:String?=null;
    var Email:String?=null;
    var Gender:String?=null;
    var Date_of_birth:String?=null;
    var Phone_number:String?=null;
    var role:String?=""
    constructor()
    constructor(
        userid: String?,
        Image: String?,
        Name: String?,
        Password: String?,
        Email: String?,
        Gender: String?,
        Date_of_birth: String?,
        Phone_number: String?,
        role: String?,
    ) {
        this.userid = userid
        this.Image = Image
        this.Name = Name
        this.Password = Password
        this.Email = Email
        this.Gender = Gender
        this.Date_of_birth = Date_of_birth
        this.Phone_number = Phone_number
        this.role = role
    }

    constructor(
        userid: String?,
        Name: String?,
        Password: String?,
        Email: String?,
        Gender: String?,
        Date_of_birth: String?,
        Phone_number: String?,
        role: String?,
    ) {
        this.userid = userid
        this.Name = Name
        this.Password = Password
        this.Email = Email
        this.Gender = Gender
        this.Date_of_birth = Date_of_birth
        this.Phone_number = Phone_number
        this.role = role
    }

    constructor(Password: String?, Email: String?) {
        this.Password = Password
        this.Email = Email
    }


}