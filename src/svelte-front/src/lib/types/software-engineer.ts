export type SoftwareEngineer = {
    id: number;
    name: string;
    techStack: string;
};

export type SoftwareEngineerRegist = Omit<SoftwareEngineer, 'id'>;
