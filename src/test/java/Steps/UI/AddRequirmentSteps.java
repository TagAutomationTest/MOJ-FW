package Steps.UI;
import Helpers.Helper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import Pages.AddRequirmentPage;
import Pages.RequirmentsListPage;

import static Helpers.Helper.getDriver;
public class AddRequirmentSteps {
    Helper helper =new Helper();
    RequirmentsListPage require=new RequirmentsListPage(getDriver());
    AddRequirmentPage add =new AddRequirmentPage(getDriver());
    @Then("Go to requirement list")
    public void GoToListingPage(){
        try {
            require.GoToListing();
        }
        catch (Exception e){
            throw e;
        }

    }
    @And("Click on Add new requirement")
    public void OpenAddrequirmentPage(){
        try {
            require.OpenAddNewRequirmentPage();
        }
        catch (Exception e) {
            throw e;
        }
    }
    @And ("Assert that Add page Opened properly")
        public void VerifyThatAddPageOpened(){
            try {
                add.VerifyAddPageOpened();
            }
            catch (Exception e) {
                throw e;
            }
        }

    }

