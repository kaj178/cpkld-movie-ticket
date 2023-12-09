package com.cpkld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieTicketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketApplication.class, args);

        // ApplicationContext context = SpringApplication.run(MovieTicketApplication.class, args);

        // // RoleService roleBean = context.getBean(RoleService.class);
        // // List<Role> list = roleBean.getAllRole();
        // // list.stream()
        // // .forEach(System.out::println);


        // //****Tao du lieu mau ơ day ae:


        // //Test du lieu bang studio:
        // //Insert studio information
        // Studio studio_01 = new StudioCreteBuilder()
        //         .setName("GCV")
        //         .setAddress("HCM")
        //         .setPhoneNumber("012345678")
        //         .setWebsite("abc/dsds/dsd/vip")
        //         .setImgUrl("tmp")
        //         .build();
        // Studio studio_02 = new StudioCreteBuilder()
        //         .setName("GCV")
        //         .setAddress("HCM")
        //         .setPhoneNumber("012345678")
        //         .setWebsite("abc/dsds/dsd/vip")
        //         .setImgUrl("tmp")
        //         .build();
        // StudioRepository studioRepository = context.getBean(StudioRepository.class);
        // studioRepository.save(studio_01);
        // studioRepository.save(studio_02);

        // //Test du lieu bang movie
        // Movie movie_01 = new MovieConcreteBuilder()
        //         .setName("T1 vo dich")
        //         .setPremier(LocalDate.of(2023, 11, 5))
        //         .setTime(LocalTime.of(1, 1, 1))
        //         .setDescription("anbcdhdhd")
        //         .setImgUrl("sdasdjsadhs/shsh")
        //         .setTrailerUrl("abc")
        //         .build();

        // MovieRepository movieRepository = context.getBean(MovieRepository.class);
        // movieRepository.save(movie_01);
    }

    // @Override
    // public void run(String... args) throws Exception {
    //     
    //     throw new UnsupportedOperationException("Unimplemented method 'run'");
    // }

}
