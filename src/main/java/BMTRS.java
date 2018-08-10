public class BMTRS {
    public static void main(String[] args) {
        focusLogin();
    }

    static void focusLogin() {
        new LoginForm().setVisible(true);
    }

    static void focusNewUserForm() {
        new NewUserForm().setVisible(true);
    }

    static void focusWelcomeForm() {
        if (CurrentUserInfo.getInstance().getAdminStatus()) {
            new AdminWelcome().setVisible(true);
        } else {
            new Welcome().setVisible(true);
        }
    }

    static void focusExhibitForm(String museumName) {
        new Exhibit(museumName).setVisible(true);
    }

    static void focusReview(String museum) {
        new Review(museum).setVisible(true);
    }

    static void focusMyTicketsForm(String userName) {
        new MyTicketsForm(userName).setVisible(true);
    }

    static void focusMyReviewsForm(String userName) {
        new MyReviewsForm(userName).setVisible(true);
    }

    static void focusManageAccountForm(String userName) {
        new ManageAccountForm(userName).setVisible(true);
    }

    static void focusDeleteAccountForm(String userName) {
        new DeleteAccountForm(userName).setVisible(true);
    }

    static void focusViewReviews(String museumName) {
        new ViewReviews(museumName).setVisible(true);
    }

    static void focusCreateExhibit(String museum) {
        new CreateExhibit(museum).setVisible(true);
    }

    static void focusMyMuseums(String name) {
        new MyMuseums(name).setVisible(true);
    }

    static void focusAcceptCuratorRequest() {
        new AcceptCuratorRequest().setVisible(true);
    }

    static void focusCuratorRequestForm(String userName) {
        new CuratorRequestForm(userName).setVisible(true);
    }

    static void focusDeleteMuseumForm(String userName) {
        new DeleteMuseumForm(userName).setVisible(true);
    }

    static void focusDeleteMuseumConfirmation(String userName, String museumName) {
        new DeleteMuseumConfirmation(userName, museumName).setVisible(true);
    }

    static void focusNewMuseumForm() {
        new NewMuseum().setVisible(true);
    }
}

