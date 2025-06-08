package com.ecommerce.product.util;




import com.ecommerce.product.models.Category;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.repositories.CategoryRepository;
import com.ecommerce.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author: ANG KUO SHENG CLEMENT
 * @date: 18-May-2025
 */

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public SetupDataLoader() {
    }

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!this.alreadySetup) {
           /* Privilege readPrivilege = this.createPrivilegeIfNotFound("READ_PRIVILEGE");
            Privilege writePrivilege = this.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
            Privilege passwordPrivilege = this.createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
            List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege);
            List<Privilege> userPrivileges = Arrays.asList(readPrivilege, passwordPrivilege);
            this.createRoleIfNotFound("ADMIN", adminPrivileges);
            this.createRoleIfNotFound("USER", userPrivileges);
            Role adminRole = this.roleRepository.findByRole("ADMIN");
            this.createUserIfNotFound("admin", "Test", "Test", "test$123", "admin@dbs-banking.com.sg", Arrays.asList(adminRole), 0.00,"active");
            Role userRole = this.roleRepository.findByRole("USER");

            User kristyYangUser = createUserIfNotFound("kristy.yang", "kristy", "yang", "p97312601z", "kristy.yang@baidu.com.cn", Arrays.asList(userRole), 3100.00,"active");
            User janetLeeUser = createUserIfNotFound("janet.lee", "janet", "lee", "p9731260z", "janet.lee@yahoo.com.tw", Arrays.asList(userRole), 1000.00,"active");
            User billGatesUser = createUserIfNotFound("bill.gates", "bill", "gates", "p97260z", "bill.gates@msn.com", Arrays.asList(userRole), 1200.00, "active");

            createContactIfNotFound(kristyYangUser, "419-800-6759", "605-794-4895", "931-313-9635");
            createContactIfNotFound(janetLeeUser, "715-662-6764", "212-934-5167", "201-709-6245");
            createContactIfNotFound(billGatesUser, "414-377-2880", "212-753-2740", "913-413-4604");

            createAccountIfNotFound(kristyYangUser, "SAVINGS",  "ACTIVE", 3508.05);
            createAccountIfNotFound(janetLeeUser, "TERM DEPOSIT",  "DORMANT", 52612.48);
            createAccountIfNotFound(billGatesUser, "INTERNET BANKING",  "ACTIVE", 159677.58);

            Address dbsMBS = createAddressIfNotFound("12 Marina Boulevard", " DBS Asia Central, Marina Bay Financial Centre Tower 3", "Marina Bay", "",  "SINGAPORE", "018982");

            createBankIfNotFound(dbsMBS, "DBS Marina Bay Sands", 7171, 061, "DBSSSGSG");
            */
            createCategoryIfNotFound("Creams and Moisturisers");
            createCategoryIfNotFound("Electronics");
            createCategoryIfNotFound("Medication and Remedies");
            createCategoryIfNotFound("Household Care & Cleaning");
            createCategoryIfNotFound("Baby & Child Care");

            createProductIfNotFound( "CeraVe Skin Renewing Night Cream | Niacinamide, Peptide Complex, and Hyaluronic Acid Moisturizer for Face | 1.7 Ounce",
                            "Help reduced the look of fine lines and wrinkles while supporting skin elasticity [ OVERNIGHT MOISTURIZER ] Helps soften, hydrate and retain skin's moisture with hyaluronic acid. Renew the look of tired skin overnight. ",
                                    "CeraVe", 29.90, 0.0, 0.0, "Creams and Moisturisers");
            createProductIfNotFound( "UGREEN 4K@60Hz HDMI 2.0 Cable 5M Braided Cord 18Gbps High Speed with Ethernet Support HDCP 2.2ARC 3D Compatible with UHD TV Monitor",
                                        "Compatible with all HDMI Ports: Compatible for Blu-Ray Player, Apple TV, Xbox One, PS5 PS4 PS3, Switch, Roku TV stick, and laptops with HDMI output to 4K UHD TV, monitor, projector and more. Reliable Signal Transmission",
                                               "UGREEN Store", 22.90, 0.0, 0.0, "Electronics");
            createProductIfNotFound( "Huggies Platinum Naturemade Pants XL, 38 count (Pack of 3)",
                    "Naturesoft Liner: With natural fibres - 100% imported from Europe. ZeroFeel Technology: Kimberly Clark's advanced technology for super absorbent ultra thin pad.",
                    "Huggies", 64.25, 0.0, 0.0, "Baby & Child Care");

            alreadySetup = true;
        }
    }


    @Transactional
    void  createProductIfNotFound(final String productName, final String Description, final String brand, double price, double discount, double specialPrice, final String categoryName) {
        Product initialProduct = this.productRepository.findByProductName(productName);
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            category = createCategoryIfNotFound(categoryName);
        }
        if (initialProduct == null) {

            //initialProduct = new Mono<Product> (productName, Description, brand, price, discount, specialPrice, category );
            initialProduct  = new Product(productName, Description, brand, price, discount, specialPrice, category);
            initialProduct.setCreatedAt(LocalDateTime.now());
            productRepository.save(initialProduct);
        }

        //return initialProduct ;
    }

    @Transactional
    Category createCategoryIfNotFound(final String categoryName) {

        Category initialCategory = categoryRepository.findByCategoryName(categoryName);
        if (initialCategory == null) {
            initialCategory = new Category(categoryName);

        }

        initialCategory = categoryRepository.save(initialCategory);
        return initialCategory ;
    }

/*
    @Transactional
    User createUserIfNotFound(final String username, final String firstName, final String lastName, final String password, final String email, final Collection<Role> roles, final Double initialDeposit, final String status) {
        User initialUser = this.userRepository.findByUsername(username);
        if (initialUser == null) {
            initialUser = new User(username, firstName, lastName, this.passwordEncoder.encode(password), email, roles, true, initialDeposit, status);
            initialUser.setCreatedOn(LocalDateTime.now());
        }

        initialUser = (User)this.userRepository.save(initialUser);
        return initialUser;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = this.privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            this.privilegeRepository.saveAndFlush(privilege);
        }

        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = this.roleRepository.findByRole(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            this.roleRepository.saveAndFlush(role);
        }

        return role;
    }

    @Transactional
    Contact createContactIfNotFound(final User user, final String homePhone, final String workPhone, final String mobilePhone) {
        Contact contact = this.contactRepository.findByUsername(user.getUsername());
        User userContact = this.userRepository.findByUsername(user.getUsername());
        if (contact == null) {
            contact = new Contact(userContact, homePhone, workPhone, mobilePhone);
        }

        contact = contactRepository.saveAndFlush(contact);
        return contact;
    }

    @Transactional
    Account createAccountIfNotFound(final User user, final String accountType, final String accountStatus, final Double accountBalance) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        Account userAccount = accountRepository.findAccountByUsername(existingUser.getUsername());

        if (userAccount == null) {
            userAccount = new Account(user, accountType, accountStatus, accountBalance);
            userAccount.setUserid(existingUser.getId());
            userAccount.setCreateDateTime(LocalDateTime.now());
        }

        userAccount = accountRepository.saveAndFlush(userAccount);
        return userAccount;
    }


    @Transactional
    Bank createBankIfNotFound( Address branchAddress, final String branchName, final Integer bankCode, final Integer branchCode, final String routingCode) {

        Bank bank = bankRepository.findBankByBankCode(7171);

        if (bank == null) {
            bank = new Bank(branchAddress, branchName, bankCode, branchCode, routingCode);
        }

        bank = bankRepository.saveAndFlush(bank);
        return bank;
    }

    @Transactional
    Address createAddressIfNotFound(final String address1, final String address2, final String city, final String state, final String country, final String zipCode) {

        Address bankAddress = addressRepository.findByAddressByBankCode(7171);

        if (bankAddress == null) {
            bankAddress = new Address(address1, address2, city, state, country, zipCode);

        }

        bankAddress= addressRepository.saveAndFlush(bankAddress);
        return bankAddress;
    }
*/





}
